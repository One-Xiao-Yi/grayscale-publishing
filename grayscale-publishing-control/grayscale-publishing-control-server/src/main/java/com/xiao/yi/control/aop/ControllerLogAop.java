package com.xiao.yi.control.aop;

import cn.hutool.json.JSONUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoyi
 * @since 2023/3/10
 */
@Aspect
@Component
public class ControllerLogAop {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAop.class);

    @Pointcut("(@annotation(org.springframework.web.bind.annotation.RequestMapping))" +
            " || (@annotation(org.springframework.web.bind.annotation.GetMapping))" +
            " || (@annotation(org.springframework.web.bind.annotation.PostMapping))" +
            " || (@annotation(org.springframework.web.bind.annotation.PutMapping))" +
            " || (@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void log() {
    }

    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        logger.info(requestURI + ": {}", JSONUtil.toJsonStr(joinPoint.getArgs()));
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            logger.info(requestURI + " error", e);
            throw new RuntimeException(e);
        }
    }

}
