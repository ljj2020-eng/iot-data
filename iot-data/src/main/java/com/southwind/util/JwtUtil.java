package com.southwind.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtUtil {
    private long expire;
    private String secret;
    private String subject;

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    public String createToken(String userId) {
        String token = Jwts.builder()
                //载荷：自定义信息
                .claim("userId", userId)
                //载荷：默认信息
                .setSubject(subject) //令牌主题
                .setExpiration(new Date(System.currentTimeMillis() + expire)) //过期时间
                .setId(UUID.randomUUID().toString())
                //签名哈希
                .signWith(SignatureAlgorithm.HS256, secret)
                //组装jwt字符串
                .compact();
        return token;
    }

    public boolean checkToken(String token){
        if(StringUtils.isEmpty(token)){
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
