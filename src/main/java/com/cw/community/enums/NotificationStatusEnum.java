package com.cw.community.enums;

public enum NotificationStatusEnum {
    READ(1),UNREAD(2);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
