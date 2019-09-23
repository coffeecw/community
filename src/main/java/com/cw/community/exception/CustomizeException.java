package com.cw.community.exception;

/**
 * created by coffeecw 2019/09/23
 * 处理上下文异常
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(CustomerErrorCode customerErrorCode) {
        this.message = customerErrorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
