package com.atguigu.system.annotation;

import com.atguigu.system.enums.BusinessType;
import com.atguigu.system.enums.OperatorType;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * Module
     */
    public String title() default "";

    /**
     * Feature
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * Operator Type
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * save request data
     */
    public boolean isSaveRequestData() default true;

    /**
     * save response data
     */
    public boolean isSaveResponseData() default true;
}
