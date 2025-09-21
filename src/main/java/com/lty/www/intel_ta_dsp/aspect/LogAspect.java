package com.lty.www.intel_ta_dsp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    // 定义切点，匹配 com.example.service 包下所有方法
    @Pointcut("execution(* com.lty.www.intel_ta_dsp.service..*(..))")
    public void serviceMethods() {}

    // 前置通知：方法执行前
    @Before("serviceMethods()")
    public void before(JoinPoint joinPoint) {
        System.out.println("💡 Before: 调用方法 " + joinPoint.getSignature());
    }

    // 后置通知：方法执行后（无论是否异常）
    @After("serviceMethods()")
    public void after(JoinPoint joinPoint) {
        System.out.println("💡 After: 方法执行完毕 " + joinPoint.getSignature());
    }

    // 返回通知：方法正常返回后
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("✅ AfterReturning: 返回结果 = " + result);
    }

    // 异常通知：方法抛异常
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("❌ AfterThrowing: 异常 = " + ex.getMessage());
    }

    // 环绕通知：方法前后都能控制
    @Around("serviceMethods()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("⏱ Around: 方法开始 " + pjp.getSignature());
        Object result = pjp.proceed(); // 执行目标方法
        System.out.println("⏱ Around: 方法结束 " + pjp.getSignature());
        return result;
    }
}
