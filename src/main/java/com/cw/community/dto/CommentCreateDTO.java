package com.cw.community.dto;

import lombok.Data;

/**
 * created by coffeecw 2019/09/24
 */
@Data
public class CommentCreateDTO {
    private Integer parentId;

    private String content;

    private Integer type;

}
