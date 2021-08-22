package com.halo.blog.exception;

/**
 * Created by halo on 2019/8/8.
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不在啦，请稍后再试"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登陆，请登陆后重试"),
    SYSTEM_ERROR(2004, "服务冒烟了，请刷新重试！"),
    TYPE_PARAM_NOT_EXIST(2005, "评论类型错误或不存在"),
    COMMENT_NOT_EXIST(2006, "你操作的评论不存在"),
    CONTENT_IS_EMPTY(2007, "评论的内容为空"),
    READ_NOTIFICATION_FAIL(2008, "哥们，你读别人信息了!"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了?"),
    File_UPLOAD_FAIL(2010, "文件莫非是不翼而飞了?");

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
