package com.atguigu.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@ApiModel(description = "分配菜单")
@Data
@Getter
public class AssignMenuVo {

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "菜单id列表")
    private List<String> menuIdList;

}
