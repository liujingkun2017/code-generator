package org.liujk.custom.code.generator.modules.order.service.impl;

import org.liujk.custom.code.generator.modules.order.entity.OrderMain;
import org.liujk.custom.code.generator.modules.order.entity.OrderCustom;
import org.liujk.custom.code.generator.modules.order.entity.OrderTicket;
import org.liujk.custom.code.generator.modules.order.mapper.OrderCustomMapper;
import org.liujk.custom.code.generator.modules.order.mapper.OrderTicketMapper;
import org.liujk.custom.code.generator.modules.order.mapper.OrderMainMapper;
import org.liujk.custom.code.generator.modules.order.service.IOrderMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 订单
 * @author： jeecg-boot
 * @date：   2019-05-12
 * @version： V1.0
 */
@Service
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {

	@Autowired
	private OrderMainMapper orderMainMapper;
	@Autowired
	private OrderCustomMapper orderCustomMapper;
	@Autowired
	private OrderTicketMapper orderTicketMapper;
	
	@Override
	@Transactional
	public void saveMain(OrderMain orderMain, List<OrderCustom> orderCustomList,List<OrderTicket> orderTicketList) {
		orderMainMapper.insert(orderMain);
		for(OrderCustom entity:orderCustomList) {
			//外键设置
			entity.setOrderId(orderMain.getId());
			orderCustomMapper.insert(entity);
		}
		for(OrderTicket entity:orderTicketList) {
			//外键设置
			entity.setOrderId(orderMain.getId());
			orderTicketMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void updateMain(OrderMain orderMain,List<OrderCustom> orderCustomList,List<OrderTicket> orderTicketList) {
		orderMainMapper.updateById(orderMain);
		
		//1.先删除子表数据
		orderCustomMapper.deleteByMainId(orderMain.getId());
		orderTicketMapper.deleteByMainId(orderMain.getId());
		
		//2.子表数据重新插入
		for(OrderCustom entity:orderCustomList) {
			//外键设置
			entity.setOrderId(orderMain.getId());
			orderCustomMapper.insert(entity);
		}
		for(OrderTicket entity:orderTicketList) {
			//外键设置
			entity.setOrderId(orderMain.getId());
			orderTicketMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		orderMainMapper.deleteById(id);
		orderCustomMapper.deleteByMainId(id);
		orderTicketMapper.deleteByMainId(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			orderMainMapper.deleteById(id);
			orderCustomMapper.deleteByMainId(id.toString());
			orderTicketMapper.deleteByMainId(id.toString());
		}
	}
	
}
