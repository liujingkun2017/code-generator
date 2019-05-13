package org.liujk.custom.code.generator.modules.order.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import org.liujk.custom.code.generator.common.aspect.annotation.FieldConfigKey;

/**
 * @Description: 产品明细
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
@Data
@TableName("order_ticket")
public class OrderTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.UUID)
    @FieldConfigKey(key = "OrderMain.id")
	private java.lang.String id;
	/**航班号*/
    @Excel(name = "航班号", width = 15)
    @FieldConfigKey(key = "OrderMain.ticketCode")
	private java.lang.String ticketCode;
	/**航班时间*/
	@Excel(name = "航班时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldConfigKey(key = "OrderMain.tickectDate")
	private java.util.Date tickectDate;
	/**外键*/
    @FieldConfigKey(key = "OrderMain.orderId")
	private java.lang.String orderId;
	/**创建人*/
    @Excel(name = "创建人", width = 15)
    @FieldConfigKey(key = "OrderMain.createBy")
	private java.lang.String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldConfigKey(key = "OrderMain.createTime")
	private java.util.Date createTime;
	/**修改人*/
    @Excel(name = "修改人", width = 15)
    @FieldConfigKey(key = "OrderMain.updateBy")
	private java.lang.String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @FieldConfigKey(key = "OrderMain.updateTime")
	private java.util.Date updateTime;
}
