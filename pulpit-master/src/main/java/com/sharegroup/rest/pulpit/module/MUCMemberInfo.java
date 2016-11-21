package com.sharegroup.rest.pulpit.module;

import com.sharegroup.rest.pulpit.utils.Affiliation;

/**
 * Created by Administrator on 2016/1/12.
 * 讲坛成员信息
 *      管理员
 *       会员
 *       属主等
 */
public class MUCMemberInfo {

    /**
     * roomId
     */
    String roomId;

    /**
     * 添加位成员
     */
    String member;

    /**
     * 成员类型
     */
    Affiliation type;

    public MUCMemberInfo(String roomId, String member) {
        this.roomId = roomId;
        this.member = member;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }


    public Affiliation getType() {
        return type;
    }

    public void setType(Affiliation type) {
        this.type = type;
    }
}
