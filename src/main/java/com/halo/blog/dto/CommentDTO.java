package com.halo.blog.dto;

import com.halo.blog.model.User;
import lombok.Data;

/**
 * Created by halo on 2019/8/12.
 * type :区分被评论的是问题还是评论
 * user : 获取头像和名字等信息
 */

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Long commentator;
    private Integer type;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private User user;
    private Integer commentCount;
}
