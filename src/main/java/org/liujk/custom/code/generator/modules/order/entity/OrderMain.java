package org.liujk.custom.code.generator.modules.order.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.liujk.custom.code.generator.common.aspect.annotation.FieldConfigKey;

/**
 * @Description: 订单
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
@Data
@TableName("order_main")
public class OrderMain implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.UUID)
    @FieldConfigKey(key = "OrderMain.id")
	private java.lang.String id;
	/**订单号*/
    @FieldConfigKey(key = "OrderMain.orderCode")
	private java.lang.String orderCode;
	/**订单类型*/
    @FieldConfigKey(key = "OrderMain.ctype")
	private java.lang.String ctype;
	/**订单日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldConfigKey(key = "OrderMain.orderDate")
	private java.util.Date orderDate;
	/**订单金额*/
    @FieldConfigKey(key = "OrderMain.orderMoney")
	private java.lang.Double orderMoney;
	/**订单备注*/
    @FieldConfigKey(key = "OrderMain.content")
	private java.lang.String content;
	/**创建人*/
    @FieldConfigKey(key = "OrderMain.createBy")
	private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldConfigKey(key = "OrderMain.createTime")
	private java.util.Date createTime;
	/**修改人*/
    @FieldConfigKey(key = "OrderMain.updateBy")
	private java.lang.String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldConfigKey(key = "OrderMain.updateTime")
	private java.util.Date updateTime;
}
