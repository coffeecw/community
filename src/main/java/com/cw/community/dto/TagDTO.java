package com.cw.community.dto;

import lombok.Data;

import java.util.List;

/**
 * created by coffeecw 2019/09/29
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
