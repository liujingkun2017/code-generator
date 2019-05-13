package org.liujk.custom.code.generator.common.aspect;

import com.alibaba.fastjson.JSON;
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
import org.liujk.custom.code.generator.common.aspect.annotation.FieldEnableKey;
import org.liujk.custom.code.generator.common.aspect.data.FieldEnableValue;
import org.liujk.custom.code.generator.common.constant.CommonConstant;
import org.liujk.custom.code.generator.common.util.RedisUtil;
import org.liujk.custom.code.generator.modules.system.service.ISysDictService;
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


/**
 * 对于接口放回的data数据，进行字段处理
 * 1、未启用的数据需要去掉
 * 2、需要进行字典转换的数据，需要进行转换后返回
 */
@Aspect
@Component
@Slf4j
public class ResponseFieldConfigAspect implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFieldConfigAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysDictService dictService;

    // 定义切点Pointcut
    @Pointcut("execution(public * org.liujk.custom.code.generator.modules.*.*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1 = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2 = System.currentTimeMillis();
        logger.debug("获取JSON数据 耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        executeConfigRule(result);
        long end = System.currentTimeMillis();
        logger.debug("解析注入JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    private void executeConfigRule(Object result) {
        if (result instanceof DefaultResponse) {
            if (((DefaultResponse) result).getData() != null) {
                //如果data是分页查询
                if (((DefaultResponse) result).getData() instanceof IPage) {
                    executeConfigRuleForPage(result);
                }
                //如果data是列表
                else if (((DefaultResponse) result).getData() instanceof List) {
                    executeConfigRuleForList(result);
                }
                //其余情况处理为data是单个对象
                else {
                    executeConfigRuleForObject(result);
                }
            }
        }
    }

    /**
     * 分页处理逻辑
     *
     * @param result
     */
    private void executeConfigRuleForPage(Object result) {
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
                //判断字段是否是启用字段,判断字段在列表是否显示
                if (field.getAnnotation(FieldEnableKey.class) != null) {
                    String fieldKey = field.getAnnotation(FieldEnableKey.class).key();
                    String fieldValue = (String) redisUtil.get(fieldKey);
                    FieldEnableValue fieldEnableValue = JSON.parseObject(fieldValue, FieldEnableValue.class);
                    if (!fieldEnableValue.isEnable() || !fieldEnableValue.isListEnable()) {
                        item.remove(field.getName());
                        continue;
                    }
                    //字段数据进行字段转换
                    if (fieldEnableValue.isDicEnable()) {
                        String code = fieldEnableValue.getDicCode();
                        String text = fieldEnableValue.getDicText();
                        String table = fieldEnableValue.getDictTable();
                        String key = String.valueOf(item.get(field.getName()));
                        String textValue = null;
                        if (!StringUtils.isEmpty(table)) {
                            textValue = dictService.queryTableDictTextByKey(table, text, code, key);
                        } else {
                            textValue = dictService.queryDictTextByKey(code, key);
                        }
                        item.put(field.getName() + "_dictText", textValue);
                    }
                }
                //date类型默认转换string格式化日期
                if (field.getType().getName().equals("java.util.Date")
                        && field.getAnnotation(JsonFormat.class) == null && item.get(field.getName()) != null) {
                    SimpleDateFormat aDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
                }
            }
            items.add(item);
        }
        ((IPage) ((DefaultResponse) result).getData()).setRecords(items);
    }

    /**
     * 列表处理逻辑
     *
     * @param result
     */
    private void executeConfigRuleForList(Object result) {
        List<JSONObject> items = new ArrayList<>();
        for (Object record : ((List) ((DefaultResponse) result).getData())) {
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
                //判断字段是否是启用字段,判断字段在列表是否显示
                if (field.getAnnotation(FieldEnableKey.class) != null) {
                    String fieldKey = field.getAnnotation(FieldEnableKey.class).key();
                    String fieldValue = (String) redisUtil.get(fieldKey);
                    FieldEnableValue fieldEnableValue = JSON.parseObject(fieldValue, FieldEnableValue.class);
                    if (!fieldEnableValue.isEnable() || !fieldEnableValue.isListEnable()) {
                        item.remove(field.getName());
                        continue;
                    }
                    //字段数据进行字段转换
                    if (fieldEnableValue.isDicEnable()) {
                        String code = fieldEnableValue.getDicCode();
                        String text = fieldEnableValue.getDicText();
                        String table = fieldEnableValue.getDictTable();
                        String key = String.valueOf(item.get(field.getName()));
                        String textValue = null;
                        if (!StringUtils.isEmpty(table)) {
                            textValue = dictService.queryTableDictTextByKey(table, text, code, key);
                        } else {
                            textValue = dictService.queryDictTextByKey(code, key);
                        }
                        item.put(field.getName() + "_dictText", textValue);
                    }
                }
                //date类型默认转换string格式化日期
                if (field.getType().getName().equals("java.util.Date")
                        && field.getAnnotation(JsonFormat.class) == null && item.get(field.getName()) != null) {
                    SimpleDateFormat aDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
                }
            }
            items.add(item);
        }
        ((DefaultResponse) result).setData(items);
    }

    /**
     * 单个对象处理逻辑
     *
     * @param result
     */
    private void executeConfigRuleForObject(Object result) {
        Object record = ((DefaultResponse) result).getData();
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
            //判断字段是否是启用字段,判断字段在列表是否显示
            if (field.getAnnotation(FieldEnableKey.class) != null) {
                String fieldKey = field.getAnnotation(FieldEnableKey.class).key();
                String fieldValue = (String) redisUtil.get(fieldKey);
                FieldEnableValue fieldEnableValue = JSON.parseObject(fieldValue, FieldEnableValue.class);
                if (!fieldEnableValue.isEnable() || !fieldEnableValue.isFormEnable()) {
                    item.remove(field.getName());
                    continue;
                }
                //字段数据进行字段转换
                if (fieldEnableValue.isDicEnable()) {
                    String code = fieldEnableValue.getDicCode();
                    String text = fieldEnableValue.getDicText();
                    String table = fieldEnableValue.getDictTable();
                    String key = String.valueOf(item.get(field.getName()));
                    String textValue = null;
                    if (!StringUtils.isEmpty(table)) {
                        textValue = dictService.queryTableDictTextByKey(table, text, code, key);
                    } else {
                        textValue = dictService.queryDictTextByKey(code, key);
                    }
                    item.put(field.getName() + "_dictText", textValue);
                }
            }
            //date类型默认转换string格式化日期
            if (field.getType().getName().equals("java.util.Date")
                    && field.getAnnotation(JsonFormat.class) == null && item.get(field.getName()) != null) {
                SimpleDateFormat aDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
            }

        }
        ((DefaultResponse) result).setData(item);

    }

    @Override
    public int getOrder() {
        return CommonConstant.FIELD_CONFIG_ASPECT_ORDERED;
    }
}
