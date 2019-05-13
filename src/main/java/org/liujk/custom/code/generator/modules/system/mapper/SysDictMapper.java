package org.liujk.custom.code.generator.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.liujk.custom.code.generator.modules.system.entity.SysDict;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 */
public interface SysDictMapper extends BaseMapper<SysDict> {


    public List<Map<String, Object>> queryDictItemsByCode(@Param("code") String code);

    public List<Map<String, Object>> queryTableDictItemsByCode(@Param("table") String table,
                                                               @Param("text") String text, @Param("code") String code);

    public String queryDictTextByKey(@Param("code") String code, @Param("key") String key);

    public String queryTableDictTextByKey(@Param("table") String table, @Param("text") String text,
                                          @Param("code") String code, @Param("key") String key);


}
