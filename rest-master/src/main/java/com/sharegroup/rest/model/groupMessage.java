package com.sharegroup.rest.model;

/**
 * Created by Administrator on 2016/1/13.
 * 群 透传消息
 */
public class groupMessage {

    /**
     * 发消息管理员账号
     */
    private String managerName;

    /**
     * 发消息管理员密码
     */
    private String managerPwd;

    /**
     * 发送的消息
     */
    private MUCCommandBody commandBody;

    /**
     * 房间
     */
    private String roomId;

    /**
     * 接收消息的用户，
     *  发送所有人，则为空
     */
    private String userNick;


    public String getManagerName() {
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
    }

    public MUCCommandBody getCommandBody() {
        return commandBody;
    }

    public void setCommandBody(MUCCommandBody commandBody) {
        this.commandBody = commandBody;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
}
