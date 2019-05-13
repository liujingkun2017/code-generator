package org.liujk.custom.code.generator.modules.order.service;

import org.liujk.custom.code.generator.modules.order.entity.OrderTicket;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 产品明细
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
public interface IOrderTicketService extends IService<OrderTicket> {

	public List<OrderTicket> selectByMainId(String mainId);
}
