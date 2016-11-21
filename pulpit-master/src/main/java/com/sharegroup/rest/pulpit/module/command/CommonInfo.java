package com.sharegroup.rest.pulpit.module.command;

import com.sharegroup.rest.pulpit.module.MUCCommandInfo;

/**
 * Created by Administrator on 2016/1/22.
 * 一对一系统消息发送
 *      如果带上用户信息,则发送给指定的用户
 *   走系统消息发送
 *
 */
public class CommonInfo extends MUCCommandInfo {


    public static Integer TYPE = 106;

    /**
     * 命令类型 default 0
     */
    private  int commondType = TYPE;

    /**
     * 命令内容
     */
    private String  value;


    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户
     */
    private String userName;

    public CommonInfo() {

    }

    public CommonInfo(String value, String userId, String userName) {
        this.value = value;
        this.userId = userId;
        this.userName = userName;
    }

    public Integer getCommondType() {
        return commondType;
    }

    public void setCommondType(Integer commondType) {
        this.commondType = commondType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
