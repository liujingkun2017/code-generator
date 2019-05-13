package org.liujk.custom.code.generator.modules.order.mapper;

import java.util.List;
import org.liujk.custom.code.generator.modules.order.entity.OrderCustom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 客户明细
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
public interface OrderCustomMapper extends BaseMapper<OrderCustom> {

	public boolean deleteByMainId(String mainId);
    
	public List<OrderCustom> selectByMainId(String mainId);
}
