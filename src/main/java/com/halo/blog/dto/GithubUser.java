package com.halo.blog.dto;

import lombok.Data;

/**
 * 当参数较多时，设置成一个class来调用
 */

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
