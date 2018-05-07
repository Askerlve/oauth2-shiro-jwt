package com.askerlve.ums.web.result;

/**
 * 系统提示信息封装类
 */
public class ResultInfo {

    public static final int TYPE_RESULT_FAIL = 0;//失败
    public static final int TYPE_RESULT_SUCCESS = 1;//成功
    public static final int TYPE_RESULT_WARN = 2;//警告
    public static final int TYPE_RESULT_INFO = 3;//提示信息


    /**
     * 消息提示类型
     */
    private int type;


    /**
     * 提示代码
     */
    private int messageCode;


    /**
     * 提示信息
     */
    private String message;

    /**
     * 结果
     */
    private Object result;


    public ResultInfo() {
    }

    public ResultInfo(int type, int messageCode, String message, Object result) {
        this.type = type;
        this.messageCode = messageCode;
        this.message = message;
        this.result = result;
    }

    public ResultInfo(int type, int messageCode, String message) {
        this.type = type;
        this.messageCode = messageCode;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}