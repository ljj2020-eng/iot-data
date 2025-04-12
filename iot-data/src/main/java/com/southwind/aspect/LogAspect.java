package com.southwind.aspect;

import com.google.gson.Gson;
import com.southwind.annotation.LogAnnotation;
import com.southwind.entity.Log;
import com.southwind.entity.User;
import com.southwind.service.LogService;
import com.southwind.util.HttpContextUtil;
import com.southwind.util.IPUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;
    public static User user;

    @Pointcut("@annotation(com.southwind.annotation.LogAnnotation)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveLog(point, (int) time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, int time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        if (logAnnotation != null) {
            //注解上的描述
            log.setOperation(logAnnotation.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = new Gson().toJson(args[0]);
            log.setParams(params);
        } catch (Exception e) {

        }

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        log.setIp(IPUtil.getIpAddr(request));
        log.setTime(time);
        //登录用户信息
        if (user != null) {
            log.setUsername(user.getUsername());
            this.logService.save(log);
        }
    }

}
