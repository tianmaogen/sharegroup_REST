package com.sharegroup.rest.bean;

/**
 * Created by Administrator on 2015/12/3.
 *
 */

/**
 * 发送的消息
 *    课件顺序改变(服务端发出)
 *    "CommondType":2,"Value":[课件数组]
 *    禁言状态改变(true表示禁言，false表示可以发言,单个用户禁言用户id不为
 *    "CommondType":3,"Value":bool,"UserId":"","UserName":""
 *    踢人(服务端发出)
 *    "CommondType":4,"Value":"讲坛id"
 *    是否开课(true表示开课，false表示结束)(服务端发出)
 *    {"CommondType":5,"Value":bool}
 *
 *
 */

public class RequestMUCCommand {

    /**
     * 发消息管理员账号
     */
//    private String managerName;

    /**
     * 发消息管理员密码
     */
//    private String managerPwd;

    /**
     * 房间
     */
    private String roomId;


    /**
     * 命令类型
     */
    private int commondType;

    /**
     * 命令消息
     */
    private Object value;

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

    /*public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPwd() {
        return managerPwd;
    }

    public void setManagerPwd(String managerPwd) {
        this.managerPwd = managerPwd;
    }*/

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

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
