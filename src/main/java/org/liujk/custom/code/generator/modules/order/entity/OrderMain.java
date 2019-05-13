package org.liujk.custom.code.generator.modules.order.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.liujk.custom.code.generator.common.aspect.annotation.FieldEnableKey;
import org.springframework.format.annotation.DateTimeFormat;

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
    @FieldEnableKey(key = "OrderMain.id")
	private java.lang.String id;
	/**订单号*/
    @FieldEnableKey(key = "OrderMain.orderCode")
	private java.lang.String orderCode;
	/**订单类型*/
    @FieldEnableKey(key = "OrderMain.ctype")
	private java.lang.String ctype;
	/**订单日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldEnableKey(key = "OrderMain.orderDate")
	private java.util.Date orderDate;
	/**订单金额*/
    @FieldEnableKey(key = "OrderMain.orderMoney")
	private java.lang.Double orderMoney;
	/**订单备注*/
    @FieldEnableKey(key = "OrderMain.content")
	private java.lang.String content;
	/**创建人*/
    @FieldEnableKey(key = "OrderMain.createBy")
	private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldEnableKey(key = "OrderMain.createTime")
	private java.util.Date createTime;
	/**修改人*/
    @FieldEnableKey(key = "OrderMain.updateBy")
	private java.lang.String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldEnableKey(key = "OrderMain.updateTime")
	private java.util.Date updateTime;
}
