package com.sharegroup.rest.model;

/**
 * Created by Administrator on 2016/1/14.
 */
public class MUCCommandBody {

    private String CommondType;

    private Object Value;

    private String UserId;

    private String UserName;

    public String getCommondType() {
        return CommondType;
    }

    public void setCommondType(String commondType) {
        CommondType = commondType;
    }

    public Object getValue() {
        return Value;
    }

    public void setValue(Object value) {
        Value = value;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
