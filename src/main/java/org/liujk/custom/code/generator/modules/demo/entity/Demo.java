package org.liujk.custom.code.generator.modules.demo.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: demo
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
@Data
@TableName("demo")
public class Demo implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键ID*/
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
	private java.lang.String name;
	/**关键词*/
	@Excel(name = "关键词", width = 15)
	private java.lang.String keyWord;
	/**打卡时间*/
	@Excel(name = "打卡时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date punchTime;
	/**工资*/
	@Excel(name = "工资", width = 15)
	private java.math.BigDecimal salaryMoney;
	/**奖金*/
	@Excel(name = "奖金", width = 15)
	private java.lang.Double bonusMoney;
	/**性别 {男:1,女:2}*/
	@Excel(name = "性别 {男:1,女:2}", width = 15)
	private java.lang.String sex;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
	private java.lang.Integer age;
	/**生日*/
	@Excel(name = "生日", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date birthday;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
	private java.lang.String email;
	/**个人简介*/
	@Excel(name = "个人简介", width = 15)
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
}
