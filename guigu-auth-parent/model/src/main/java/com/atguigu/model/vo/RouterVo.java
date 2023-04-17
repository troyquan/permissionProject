package com.atguigu.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 路由配置信息
 *
 */
@Data
public class RouterVo
{
    /**
     * router name
     */
    //private String name;

    /**
     * router path
     */
    private String path;

    /**
     * ishidden
     */
    private boolean hidden;

    /**
     * component address
     */
    private String component;

    /**
     * show nested
     */
    private Boolean alwaysShow;


    private MetaVo meta;

    /**
     * children router
     */
    private List<RouterVo> children;


}
