package com.sharegroup.rest.bean;

import com.sharegroup.rest.utils.Affiliation;

/**
 * Created by Administrator on 2016/1/12.
 * 讲坛成员信息
 *      管理员
 *       会员
 *       属主等
 */
public class RequestMemberInfo {

    /**
     * roomId
     */
    String roomId;

    /**
     * 添加位成员
     */
    String member;

    /**
     * 操作类型
     */
    Affiliation type;

    public Affiliation getType() {
        return type;
    }

    public void setType(Affiliation type) {
        this.type = type;
    }


    /*MUCType type;

    public MUCType getType() {
        return type;
    }

    public void setType(MUCType type) {
        this.type = type;
    }*/

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



}
