package com.cw.community.exception;

public enum CustomerErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FIND("您找的问题可能不存在了");
    private String  message;

    CustomerErrorCode(String message) {
        this.message=message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
