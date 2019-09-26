package com.cw.community.dto;

import com.cw.community.model.User;
import lombok.Data;

/**
 * created by coffeecw 2019/09/25
 */
@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private String content;
    private Long gmtModified;
    private Long likeCount;
    private Long gmtCreate;
    private User user;
}
