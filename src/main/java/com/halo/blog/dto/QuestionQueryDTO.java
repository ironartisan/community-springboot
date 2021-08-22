package com.halo.blog.dto;

import lombok.Data;

/**
 * Created by halo on 2019/8/30.
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
