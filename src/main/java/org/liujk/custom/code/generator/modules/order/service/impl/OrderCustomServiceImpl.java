package org.liujk.custom.code.generator.modules.order.service.impl;

import org.liujk.custom.code.generator.modules.order.entity.OrderCustom;
import org.liujk.custom.code.generator.modules.order.mapper.OrderCustomMapper;
import org.liujk.custom.code.generator.modules.order.service.IOrderCustomService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 客户明细
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
@Service
public class OrderCustomServiceImpl extends ServiceImpl<OrderCustomMapper, OrderCustom> implements IOrderCustomService {
	
	@Autowired
	private OrderCustomMapper orderCustomMapper;
	
	@Override
	public List<OrderCustom> selectByMainId(String mainId) {
		return orderCustomMapper.selectByMainId(mainId);
	}
}
