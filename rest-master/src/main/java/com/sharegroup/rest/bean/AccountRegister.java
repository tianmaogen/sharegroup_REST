package com.sharegroup.rest.bean;

/**
 * Created by Administrator on 2015/12/8.
 * 注册
 */
public class AccountRegister {
    /**
     * 注册用户名
     */
    private String userName;

    /**
     * 注册的用户密码
     */
    private String userPwd;

    /**
     * 确认密码
     */

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

}
