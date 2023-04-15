package com.atguigu.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomizedException extends RuntimeException{

    private Integer code;

    private String msg;
}
