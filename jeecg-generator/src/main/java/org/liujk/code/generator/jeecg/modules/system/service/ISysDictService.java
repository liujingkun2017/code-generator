package org.liujk.code.generator.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liujk.code.generator.jeecg.modules.system.entity.SysDict;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author zhangweijian
 * @since 2018-12-28
 */
public interface ISysDictService extends IService<SysDict> {


    String queryDictTextByKey(String code, String key);

    String queryTableDictTextByKey(String table, String text, String code, String key);


}
