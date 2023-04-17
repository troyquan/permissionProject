package com.atguigu.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@ApiModel(description = "Assign Role")
@Data

public class AssignRoleVo {

    @ApiModelProperty(value = "user Id")
    private String userId;

    @ApiModelProperty(value = "user id list")
    private List<String> roleIdList;

}
