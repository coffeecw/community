package com.cw.community.dto;

import lombok.Data;

/**
 * 请求参数封装
 */
@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;
}
