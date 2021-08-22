package com.halo.blog.dto;

import com.halo.blog.model.User;
import lombok.Data;

/**
 * Created by halo on 2019/8/5.
 */

@Data
public class T {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;


    private Long modified;
    private Long creator;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private User user;
}

