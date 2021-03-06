package com.cw.community.exception;

/**
 * 枚举实现接口
 */
public enum CustomerErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FIND("您找的问题可能不存在了,要不要换个试试",1001),
    NO_LOGIN("当前操作需要登录，请登陆后重试", 1002),
    SYS_ERROR("服务器冒烟了,请稍后再试试",1003),
    TARGET_PARAM_NOT_FOUND("未选中任何问题或评论进行回复",1004 ),
    TYPE_PARAM_WRONG("评论类型错误或不存在", 1005),
    COMMENT_NOT_FOUND("回复的评论不存在了，要不要换个试试？",1006 ),
    CONTENT_NOT_EMPTY("回复的内容不能为空！" ,1007),
    READ_NOTIFICATION_FAIL("兄弟你这是读别人信息呢？" ,1008),
    NOTIFICATION_NOT_FIND("消息莫非不翼而飞了？",1009),
    FiLE_UPLOAD_FAIL("图片上传失败",1010)
    ;
    private String  message;
    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }

    CustomerErrorCode(String message, Integer code) {
        this.message = message;
        this.code = code;
    }


}
