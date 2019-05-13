package org.liujk.custom.code.generator.modules.order.mapper;

import java.util.List;
import org.liujk.custom.code.generator.modules.order.entity.OrderTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 产品明细
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
public interface OrderTicketMapper extends BaseMapper<OrderTicket> {

	public boolean deleteByMainId(String mainId);
    
	public List<OrderTicket> selectByMainId(String mainId);
}
