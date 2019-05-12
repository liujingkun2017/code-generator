package org.liujk.custom.code.generator.modules.demo.service.impl;

import org.liujk.custom.code.generator.modules.demo.entity.Demo;
import org.liujk.custom.code.generator.modules.demo.mapper.DemoMapper;
import org.liujk.custom.code.generator.modules.demo.service.IDemoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: demo
 * @author： jeecg-boot
 * @date：   2019-05-12
 * @version： V1.0
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

}
