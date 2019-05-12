package org.liujk.custom.code.generator.modules.order.service;

import org.liujk.custom.code.generator.modules.order.entity.OrderCustom;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 客户明细
 * @author： jeecg-boot
 * @date：   2019-05-12
 * @version： V1.0
 */
public interface IOrderCustomService extends IService<OrderCustom> {

	public List<OrderCustom> selectByMainId(String mainId);
}
