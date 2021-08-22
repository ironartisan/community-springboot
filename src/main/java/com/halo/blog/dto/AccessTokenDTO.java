package com.halo.blog.dto;

import lombok.Data;

/**
 * class与class之间网络类型传输用dto
 * 数据库中用model
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}

