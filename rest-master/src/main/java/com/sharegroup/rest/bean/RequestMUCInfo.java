package com.sharegroup.rest.bean;

/**
 * Created by Administrator on 2016/1/12.
 * 对外公开参数列表，供其他系统调用
 *  以此来管理讲坛
 */
public class RequestMUCInfo {

    /**
     * 安全验证参数
     */
    //String token;
    /**
     * 房间ID
     */
    String roomId;
    /**
     * 房间名称 ，与roomId对应
     */
    String roomName;
    /**
     * 房间描述
     */
    String desc;
    /**
     * 房间主题
     */
    String subject;

    /**
     * 房间人数限制
     */
    Integer maxUsers;

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }
}
