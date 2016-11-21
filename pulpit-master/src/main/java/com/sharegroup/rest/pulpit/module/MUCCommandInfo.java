package com.sharegroup.rest.pulpit.module;

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

public class MUCCommandInfo {

    /**
     * 发消息管理员账号
     */
    private String managerName;

    /**
     * 发消息管理员密码
     */
    private String managerPwd;

    /**
     * 房间 此属性需要组装到message对象中的ID
     */
    private String roomId;


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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
