package org.liujk.custom.code.generator.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.liujk.custom.code.generator.common.api.DefaultResponse;
import org.liujk.custom.code.generator.common.aspect.annotation.Dict;
import org.liujk.custom.code.generator.common.aspect.annotation.FieldConfigKey;
import org.liujk.custom.code.generator.common.constant.CommonConstant;
import org.liujk.custom.code.generator.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Aspect
@Component
@Slf4j
public class FieldConfigKeyAspect implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(FieldConfigKeyAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    // 定义切点Pointcut
    @Pointcut("execution(public * org.liujk.custom.code.generator.modules.*.*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        long time1 = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2 = System.currentTimeMillis();
        log.debug("获取JSON数据 耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        executeConfigRule(result);
        long end = System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    private void executeConfigRule(Object result) {
        if (result instanceof DefaultResponse) {
            //如果data是分页查询
            if (((DefaultResponse) result).getData() instanceof IPage) {
                List<JSONObject> items = new ArrayList<>();
                for (Object record : ((IPage) ((DefaultResponse) result).getData()).getRecords()) {
                    ObjectMapper mapper = new ObjectMapper();
                    String json = "{}";
                    try {
                        //解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
                        json = mapper.writeValueAsString(record);
                    } catch (JsonProcessingException e) {
                        logger.error("json解析失败" + e.getMessage());
                        e.printStackTrace();
                    }
                    JSONObject item = JSONObject.parseObject(json);
                    for (Field field : record.getClass().getDeclaredFields()) {
                        if (field.getAnnotation(FieldConfigKey.class) != null) {
                            item.remove(field.getName());
                        }
                    }
                    items.add(item);
                }
                ((IPage) ((DefaultResponse) result).getData()).setRecords(items);
            }
            //如果data是列表
            else if (((DefaultResponse) result).getData() instanceof List) {

            }
            //其余情况处理为data是单个对象
            else {

            }

        }
    }

    @Override
    public int getOrder() {
        return CommonConstant.FIELD_CONFIG_KEY_ASPECT_ORDERED;
    }
}
