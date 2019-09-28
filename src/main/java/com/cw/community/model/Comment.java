package com.cw.community.model;

import lombok.Data;

/**
 * created by coffeecw 2019/09/24
 */
@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private String content;
    private Long gmtModified;
    private Long likeCount;
    private Long gmtCreate;
    private Integer commentCount;
}
