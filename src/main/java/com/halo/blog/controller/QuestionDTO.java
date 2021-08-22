package com.halo.blog.controller;

import com.halo.blog.model.User;
import lombok.Data;

/**
 * Created by halo on 2019/8/28.
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}

