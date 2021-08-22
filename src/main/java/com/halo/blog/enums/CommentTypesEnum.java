package com.halo.blog.enums;

/**
 * Created by halo on 2019/8/9.
 */

public enum CommentTypesEnum {
    /*

     */

    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer type) {
        // 遍历enum类型的取值
        for (CommentTypesEnum commentTypesEnum : CommentTypesEnum.values()) {
            if (commentTypesEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypesEnum(Integer type) {
        this.type = type;

    }

}
