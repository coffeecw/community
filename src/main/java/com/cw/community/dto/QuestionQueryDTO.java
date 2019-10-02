package com.cw.community.dto;

import lombok.Data;

/**
 * created by coffeecw 2019/10/02
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
