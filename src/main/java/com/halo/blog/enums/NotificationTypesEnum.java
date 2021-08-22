package com.halo.blog.enums;

/**
 * type：标记是否已经查看
 * name: 具体说明通知类型
 * ALt Insert 添加构造方法NotificationTypesEnum和get方法
 * Created by halo on 2019/8/27.
 */
public enum NotificationTypesEnum {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论");

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypesEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypesEnum notificationTypesEnum : NotificationTypesEnum.values()) {
            if (notificationTypesEnum.getType() == type) {
                return notificationTypesEnum.getName();
            }
        }
        return "";
    }
}
