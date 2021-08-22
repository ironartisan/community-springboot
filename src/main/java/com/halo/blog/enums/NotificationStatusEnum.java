package com.halo.blog.enums;

/**
 * Created by halo on 2019/8/27.
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
