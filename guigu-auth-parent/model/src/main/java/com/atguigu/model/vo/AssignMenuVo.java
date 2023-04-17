package com.atguigu.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@ApiModel(description = "Assign Menu")
@Data
@Getter
public class AssignMenuVo {

    @ApiModelProperty(value = "Role id")
    private String roleId;

    @ApiModelProperty(value = "Menu Id")
    private List<String> menuIdList;

}
