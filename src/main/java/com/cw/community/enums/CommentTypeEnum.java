package com.cw.community.enums;

public enum CommentTypeEnum {
    //回复问题
    QUESTION(1),
    //回复评论
    COMMENT(2);
    private Integer type;
    //判断类型是否一致
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
