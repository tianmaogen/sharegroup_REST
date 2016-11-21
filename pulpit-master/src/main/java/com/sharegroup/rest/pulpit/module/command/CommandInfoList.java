package com.sharegroup.rest.pulpit.module.command;


import java.util.List;

public class CommandInfoList {

    private CommonInfo command;

    private List<String> userList;

    public CommonInfo getCommand() {
        return command;
    }

    public void setCommand(CommonInfo command) {
        this.command = command;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }
}
