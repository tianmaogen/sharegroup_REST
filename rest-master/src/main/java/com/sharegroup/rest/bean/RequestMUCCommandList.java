package com.sharegroup.rest.bean;


import java.util.List;

public class RequestMUCCommandList {

    private RequestMUCCommand command;

    private List<String> userList;

    public RequestMUCCommand getCommand() {
        return command;
    }

    public void setCommand(RequestMUCCommand command) {
        this.command = command;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }
}
