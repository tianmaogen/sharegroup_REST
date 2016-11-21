package com.sharegroup.rest.pulpit.module.command;

import com.sharegroup.rest.pulpit.module.MUCCommandInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/15.
 * MUC 命令
 * 踢人(服务端发出)
 */
public class OutcastInfo extends MUCCommandInfo implements Serializable {

    public static Integer TYPE = 3;

    /**
     * 命令类型 default 3
     */
    private  int commondType = TYPE;

    /**
     * 讲坛id
     */
    private String  value;


    /**
     * 要踢出的用户ID
     *   必填项 ，如果是踢出所有人，则建议删除讲坛
     */
    private String userId;

    /**
     * 要踢出的用户
     */
    private String userName;

    public int getCommondType() {
        return commondType;
    }

    public void setCommondType(int commondType) {
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
