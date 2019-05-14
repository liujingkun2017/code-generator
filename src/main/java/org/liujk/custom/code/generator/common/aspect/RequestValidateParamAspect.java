package org.liujk.custom.code.generator.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.liujk.custom.code.generator.common.aspect.annotation.FieldEnableKey;
import org.liujk.custom.code.generator.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
@Slf4j
public class RequestValidateParamAspect implements Ordered {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int getOrder() {
        return 2;
    }

    // 定义切点Pointcut
    // todo定义的切点还需要根据实际情况调整
    @Pointcut("execution(public * org.liujk.custom.code.generator.modules.*.*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {


        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {

        }
        methodSignature = (MethodSignature) signature;

        Class[] classes = methodSignature.getParameterTypes();
        Class demoClass = classes[0];
        Field[] fields = demoClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(FieldEnableKey.class) != null) {
                String fieldKey = field.getAnnotation(FieldEnableKey.class).key();
                String fieldValue = (String) redisUtil.get(fieldKey);
                System.out.println("------" + fieldValue +
                        "------");
                //todo执行校验逻辑
            }
        }


        return pjp.proceed();
    }

}
