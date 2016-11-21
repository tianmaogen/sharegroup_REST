package com.sharegroup.rest.pulpit.module.command;

import com.sharegroup.rest.pulpit.module.MUCCommandInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 * MUC 命令
 * 课件顺序改变(服务端发出) 讲师与管理员之间对发
 */
public class CourseWareInfo extends MUCCommandInfo implements Serializable {

    private static final long serialVersionUID = 1844028016771088562L;

    public static Integer TYPE = 1;

    /**
     * 命令类型 default 1
     */
    private  Integer commondType = TYPE;

    /**
     * 课件数组
     * List<String>
     */
    Object  value;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户
     */
    private String userName;

    public int getCommondType() {
        return commondType;
    }

    public void setCommondType(int commondType) {
        this.commondType = commondType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
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
