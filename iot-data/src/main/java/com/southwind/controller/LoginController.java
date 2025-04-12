package com.southwind.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.southwind.aspect.LogAspect;
import com.southwind.entity.User;
import com.southwind.form.LoginForm;
import com.southwind.service.UserService;
import com.southwind.util.JwtUtil;
import com.southwind.util.Result;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/captcha")
    public Result captcha(){
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String code = specCaptcha.text().toLowerCase();
        String uuid = IdUtil.simpleUUID();
        //存入redis并设置过期时间为2分钟
        this.redisTemplate.opsForValue().set(uuid, code, 120, TimeUnit.SECONDS);
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("uuid", uuid);
        map.put("code", code);
        map.put("captcha", specCaptcha.toBase64());
        return Result.ok().put("data", map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpSession session){
        //验证码校验
        String code = (String) this.redisTemplate.opsForValue().get(loginForm.getUuid());
        //判断验证码是否有效
        if(code == null){
            return Result.error("验证码已过期");
        }
        //判断验证码是否正确
        if(!code.equals(loginForm.getCaptcha())){
            return Result.error("验证码错误");
        }
        //判断用户名是否正确
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginForm.getUsername());
        User user = this.userService.getOne(queryWrapper);
        if(user == null){
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        String password = SecureUtil.sha256(loginForm.getPassword());
        if(!password.equals(user.getPassword())){
            return Result.error("密码错误");
        }
        //验证用户是否可用
        if(user.getStatus() == 0) {
            return Result.error("账号已被锁定，请联系管理员");
        }
        //登录成功
        session.setAttribute("user", user);
        //创建token
        String token = this.jwtUtil.createToken(String.valueOf(user.getUserId()));
        this.redisTemplate.opsForValue().set("communityuser-"+user.getUserId(), token,jwtUtil.getExpire());
        Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtil.getExpire());
        LogAspect.user = user;
        return Result.ok().put("data", map);
    }

    @GetMapping("/checkToken")
    public Result checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        boolean result = this.jwtUtil.checkToken(token);
        if(result) return Result.ok().put("status", "ok");
        return Result.ok().put("status", "error");
    }

    @PostMapping("/logout")
    public Result logout(HttpSession session){
        session.invalidate();
        return Result.ok();
    }

}
