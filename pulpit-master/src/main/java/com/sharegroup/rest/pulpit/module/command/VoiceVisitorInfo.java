package com.sharegroup.rest.pulpit.module.command;

import com.sharegroup.rest.pulpit.module.MUCCommandInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/15.
 * MUC 命令
 * 禁言状态改变(true表示禁言，false表示可以发言,单个用户禁言用户id不为null，全体禁言用户id为null)
 */
public class VoiceVisitorInfo extends MUCCommandInfo implements Serializable {

    private static final long serialVersionUID = -719982267644368298L;
    public static Integer TYPE = 2;

    /**
     * 命令类型 default 2
     */
    private  int commondType = TYPE;

    /**
     * 是否禁言
     * true表示禁言，false表示可以发
     */
    private boolean value;


    /**
     * 接收消息的用户，
     *  发送所有人，则为空
     */
    private String userId;

    /**
     * 接收消息的用户，
     *  发送所有人，则为空
     */
    private String userName;


    public int getCommondType() {
        return commondType;
    }

    public void setCommondType(int commondType) {
        this.commondType = commondType;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
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
