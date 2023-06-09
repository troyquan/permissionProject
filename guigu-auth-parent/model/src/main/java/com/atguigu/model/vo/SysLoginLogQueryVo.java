package com.atguigu.model.vo;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SysLoginLogQueryVo {
	
	@ApiModelProperty(value = "user account")
	private String username;

	private String createTimeBegin;
	private String createTimeEnd;

}

