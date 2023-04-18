package com.atguigu.system.exception;

import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody//return jason
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("Ops Something went wrong");
    }
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e ){
        e.printStackTrace();
        return Result.fail().message("ArithmeticException");
    }

    @ExceptionHandler(CustomizedException.class)
    @ResponseBody
    public Result error(CustomizedException e ){
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("No Permission");
    }
}
