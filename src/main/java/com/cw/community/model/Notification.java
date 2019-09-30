package com.cw.community.model;

import lombok.Data;

/**
 * created by coffeecw 2019/09/30
 */
@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}

