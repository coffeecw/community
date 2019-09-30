package com.cw.community.dto;

import com.cw.community.model.User;
import lombok.Data;

/**
 * created by coffeecw 2019/09/30
 */
@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Integer notifier;
    private String outerTitle;
    private String notifierName;
    private Integer outerId;
    private String typeName;
    private Integer type;
}
