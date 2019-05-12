package org.liujk.custom.code.generator.modules.order.service;

import org.liujk.custom.code.generator.modules.order.entity.OrderCustom;
import org.liujk.custom.code.generator.modules.order.entity.OrderTicket;
import org.liujk.custom.code.generator.modules.order.entity.OrderMain;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 订单
 * @author： jeecg-boot
 * @date：   2019-05-12
 * @version： V1.0
 */
public interface IOrderMainService extends IService<OrderMain> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(OrderMain orderMain,List<OrderCustom> orderCustomList,List<OrderTicket> orderTicketList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(OrderMain orderMain,List<OrderCustom> orderCustomList,List<OrderTicket> orderTicketList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
