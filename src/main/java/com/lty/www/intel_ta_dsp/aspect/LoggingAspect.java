package com.lty.www.intel_ta_dsp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    private static final String LOG_DIR = "/home/yulierren/Project/Intel_Ta_Dsp/log";
    private static final String LOG_FILE = LOG_DIR + "/application.log";

    static {
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Pointcut("execution(* com.lty.www.intel_ta_dsp.controller..*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logToFile("调用方法: " + joinPoint.getSignature() + " 参数: " + java.util.Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logToFile("方法返回: " + joinPoint.getSignature() + " 返回值: " + result);
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logToFile("方法异常: " + joinPoint.getSignature() + " 异常: " + ex.getMessage());
    }

    private void logToFile(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(LocalDateTime.now() + " - " + message + System.lineSeparator());
        } catch (IOException e) {
            // 可以选择打印到控制台或忽略
            e.printStackTrace();
        }
    }
}
