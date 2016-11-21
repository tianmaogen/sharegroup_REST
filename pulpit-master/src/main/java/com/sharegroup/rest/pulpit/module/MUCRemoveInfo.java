package com.sharegroup.rest.pulpit.module;

/**
 * Created by Administrator on 2016/1/12.
 * 对外公开参数列表，供其他系统调用
 *  以此来管理讲坛
 */
public class MUCRemoveInfo {

    /**
     * 讲坛属主，只有属主可以删除自己的讲坛
     */
    String owner;
    /**
     * 房间ID
     */
    String roomId;


    public MUCRemoveInfo(String owner, String roomId) {
        this.owner = owner;
        this.roomId = roomId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
