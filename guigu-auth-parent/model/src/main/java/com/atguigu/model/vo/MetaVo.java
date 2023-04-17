package com.atguigu.model.vo;

import lombok.Data;

/**
 * 路由显示信息
 *
 */
@Data
public class MetaVo
{
    /**
     * set router name
     */
    private String title;

    /**
     * router icon src/assets/icons/svg
     */
    private String icon;

    public MetaVo()
    {
    }

    public MetaVo(String title, String icon)
    {
        this.title = title;
        this.icon = icon;
    }

}

