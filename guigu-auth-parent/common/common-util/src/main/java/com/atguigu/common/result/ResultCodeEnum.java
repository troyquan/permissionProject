package com.atguigu.common.result;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"Operation Successfully"),
    FAIL(201, "Operation Failed"),
    SERVICE_ERROR(2012, "Service Exception"),
    DATA_ERROR(204, "Data Exception"),
    ILLEGAL_REQUEST(205, "Invalid Request"),
    REPEAT_SUBMIT(206, "Duplicate Submission"),
    ARGUMENT_VALID_ERROR(210, "Parameter Invalid"),

    LOGIN_AUTH(208, "You are not logged in"),
    PERMISSION(209, "No Permission"),
    ACCOUNT_ERROR(214, "Incorrect Account"),
    PASSWORD_ERROR(215, "Incorrect password"),
    LOGIN_MOBLE_ERROR( 216, "Invalid Account"),
    ACCOUNT_STOP( 217, "Account Suspended"),
    NODE_ERROR( 218, "Child nodes inside, cant delete")
            ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
