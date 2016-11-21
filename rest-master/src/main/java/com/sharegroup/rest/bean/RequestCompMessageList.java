package com.sharegroup.rest.bean;

import java.util.List;

/**
 * .订阅号向用户发送消息
 * Created by Administrator on 2015/12/7.
 * <p/>
 */
public class RequestCompMessageList {

    List<String> userList;

    RequestCompMessage compMessage;

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public RequestCompMessage getCompMessage() {
        return compMessage;
    }

    public void setCompMessage(RequestCompMessage compMessage) {
        this.compMessage = compMessage;
    }
}
