package com.cw.community.exception;

/**
 * created by coffeecw 2019/09/23
 * 处理上下文异常
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(CustomerErrorCode customerErrorCode) {
        this.message = customerErrorCode.getMessage();
        this.code=customerErrorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }
    //重写父类的getMessage方法
    @Override
    public String getMessage() {
        return message;
    }
    public CustomizeException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }


}
