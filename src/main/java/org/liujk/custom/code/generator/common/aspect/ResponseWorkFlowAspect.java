package org.liujk.custom.code.generator.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

public class ResponseWorkFlowAspect implements Ordered {

    @Override
    public int getOrder() {
        return 0;
    }

    // 定义切点Pointcut
    @Pointcut("execution(public * org.liujk.custom.code.generator.modules.*.*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();



        return result;
    }
}
