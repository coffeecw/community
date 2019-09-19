package com.cw.community.dto;

import lombok.Data;

/**
 * 请求参数封装
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
