package org.liujk.code.generator.jeecg.modules.user.entity;

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
 * @Description: user_info
 * @author： jeecg-boot
 * @date：   2020-12-03
 * @version： V1.0
 */
@Data
@TableName("user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.UUID)
	private java.lang.Integer id;
	/**username*/
	@Excel(name = "username", width = 15)
	private java.lang.String username;
	/**password*/
	@Excel(name = "password", width = 15)
	private java.lang.String password;
	/**realname*/
	@Excel(name = "realname", width = 15)
	private java.lang.String realname;
	/**age*/
	@Excel(name = "age", width = 15)
	private java.lang.Integer age;
	/**gender*/
	@Excel(name = "gender", width = 15)
	private java.lang.String gender;
	/**height*/
	@Excel(name = "height", width = 15)
	private java.lang.Integer height;
	/**weight*/
	@Excel(name = "weight", width = 15)
	private java.lang.Integer weight;
	/**createdBy*/
	@Excel(name = "createdBy", width = 15)
	private java.lang.String createdBy;
	/**updatedBy*/
	@Excel(name = "updatedBy", width = 15)
	private java.lang.String updatedBy;
	/**createdTime*/
	@Excel(name = "createdTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createdTime;
	/**updatedTime*/
	@Excel(name = "updatedTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updatedTime;
	/**isDeleted*/
	@Excel(name = "isDeleted", width = 15)
	private java.lang.Integer isDeleted;
}
