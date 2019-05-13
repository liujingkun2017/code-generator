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

/**
 * @Description: 客户明细
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
@Data
@TableName("a_order_customer")
public class OrderCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/**客户名*/
    @Excel(name = "客户名", width = 15)
	private java.lang.String name;
	/**性别*/
    @Excel(name = "性别", width = 15)
	private java.lang.String sex;
	/**身份证号码*/
    @Excel(name = "身份证号码", width = 15)
	private java.lang.String idcard;
	/**身份证扫描件*/
    @Excel(name = "身份证扫描件", width = 15)
	private java.lang.String idcardPic;
	/**电话1*/
    @Excel(name = "电话1", width = 15)
	private java.lang.String telphone;
	/**外键*/
	private java.lang.String orderId;
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
}
