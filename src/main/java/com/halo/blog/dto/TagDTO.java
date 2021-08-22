package com.halo.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by halo on 2019/8/26.
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
