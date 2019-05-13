package org.liujk.custom.code.generator.modules.order.service.impl;

import org.liujk.custom.code.generator.modules.order.entity.OrderTicket;
import org.liujk.custom.code.generator.modules.order.mapper.OrderTicketMapper;
import org.liujk.custom.code.generator.modules.order.service.IOrderTicketService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 产品明细
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
@Service
public class OrderTicketServiceImpl extends ServiceImpl<OrderTicketMapper, OrderTicket> implements IOrderTicketService {
	
	@Autowired
	private OrderTicketMapper orderTicketMapper;
	
	@Override
	public List<OrderTicket> selectByMainId(String mainId) {
		return orderTicketMapper.selectByMainId(mainId);
	}
}
