package com.halo.blog.dto;

import lombok.Data;

/**
 * id 通知ID
 * gmtCreate 创建时间
 * status 标记读状态，0为未读，1为已读
 * notifier 通知人ID
 * notifierName 评论者的名称
 * outerTitle 问题或评论标题
 * outerid 被评论的问题或评论的ID
 * typeName 类型名称
 * type 通知类型
 * Created by halo on 2019/8/27.
 */


@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
