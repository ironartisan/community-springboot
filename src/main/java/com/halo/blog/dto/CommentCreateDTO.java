package com.halo.blog.dto;

import lombok.Data;

/**
 * Created by halo on 2019/8/9.
 * TODO: parentId取不到值，增加id字段来取值
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
    private Long id;
}
