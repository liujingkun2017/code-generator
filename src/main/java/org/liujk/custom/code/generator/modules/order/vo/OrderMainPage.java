package org.liujk.custom.code.generator.modules.order.vo;

import java.util.List;
import org.liujk.custom.code.generator.modules.order.entity.OrderMain;
import org.liujk.custom.code.generator.modules.order.entity.OrderCustom;
import org.liujk.custom.code.generator.modules.order.entity.OrderTicket;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
public class OrderMainPage {
	
	/**主键*/
	private java.lang.String id;
	/**订单号*/
  	@Excel(name = "订单号", width = 15)
	private java.lang.String orderCode;
	/**订单类型*/
  	@Excel(name = "订单类型", width = 15)
	private java.lang.String ctype;
	/**订单日期*/
  	@Excel(name = "订单日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date orderDate;
	/**订单金额*/
  	@Excel(name = "订单金额", width = 15)
	private java.lang.Double orderMoney;
	/**订单备注*/
  	@Excel(name = "订单备注", width = 15)
	private java.lang.String content;
	/**创建人*/
  	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建时间*/
  	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**修改人*/
  	@Excel(name = "修改人", width = 15)
	private java.lang.String updateBy;
	/**修改时间*/
  	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	
	@ExcelCollection(name="客户明细")
	private List<OrderCustom> orderCustomList;
	@ExcelCollection(name="产品明细")
	private List<OrderTicket> orderTicketList;
	
}
