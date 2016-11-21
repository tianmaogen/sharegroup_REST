package com.sharegroup.rest.pulpit.module.command;

import com.sharegroup.rest.pulpit.module.MUCCommandInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/15.
 * MUC 命令
 * 电子白板中的课件（PPT）改变，发送通知命令，让听课的人改变课件与讲师同步
 *
 */
public class WhiteBoardInfo extends MUCCommandInfo implements Serializable {

    
    public static Integer TYPE = 5;

    /**
     * 命令类型 default 5
     */
    private  int commondType = TYPE;

    /**
     * 要改变的PPT地址
     */
    private String value;

    public int getCommondType() {
        return commondType;
    }

    public void setCommondType(int commondType) {
        this.commondType = commondType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
