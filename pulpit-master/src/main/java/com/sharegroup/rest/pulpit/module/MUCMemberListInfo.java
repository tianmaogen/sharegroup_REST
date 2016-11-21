package com.sharegroup.rest.pulpit.module;

import com.sharegroup.rest.pulpit.utils.Affiliation;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 * 讲坛成员信息
 *      管理员
 *       会员
 *       属主等
 */
public class MUCMemberListInfo {

    /**
     * roomId
     */
    String roomId;

    /**
     * 成员列表
     */
    List<String> members;


    /**
     * 成员类型
     */
    Affiliation type;

    public MUCMemberListInfo(String roomId, List<String> members) {
        this.roomId = roomId;
        this.members = members;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Affiliation getType() {
        return type;
    }

    public void setType(Affiliation type) {
        this.type = type;
    }
}
