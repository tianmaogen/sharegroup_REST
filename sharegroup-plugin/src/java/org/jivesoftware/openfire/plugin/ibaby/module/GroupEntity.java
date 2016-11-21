package org.jivesoftware.openfire.plugin.ibaby.module;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-1-13
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 * 推送消息接口
 */
public class GroupEntity {


    //讲坛ID(roomID)
    private String roomId;

    //验证用户
    private String userNick;

    public GroupEntity() {

    }

    public GroupEntity(String roomId, String userNick) {
        this.roomId = roomId;
        this.userNick = userNick;
    }

    @Override
    public String toString() {
        StringBuffer sbf = new StringBuffer("?");

        sbf.append("pulpitId=" + this.roomId );
        sbf.append("&userId=" + this.userNick);
        return sbf.toString();
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
