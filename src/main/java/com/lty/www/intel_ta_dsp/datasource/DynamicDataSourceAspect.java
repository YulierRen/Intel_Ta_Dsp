package com.lty.www.intel_ta_dsp.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    @Around("@annotation(com.lty.www.intel_ta_dsp.datasource.Master)")
    public Object setMaster(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            DynamicDataSourceContextHolder.set(DataSourceType.MASTER);
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.clear();
        }
    }

    @Around("@annotation(com.lty.www.intel_ta_dsp.datasource.Slave)")
    public Object setSlave(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            DynamicDataSourceContextHolder.set(DataSourceType.SLAVE);
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.clear();
        }
    }
}
