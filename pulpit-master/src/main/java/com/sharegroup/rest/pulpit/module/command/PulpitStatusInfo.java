package com.sharegroup.rest.pulpit.module.command;

import com.sharegroup.rest.pulpit.module.MUCCommandInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/15.
 * MUC 命令
 * 是否开课(true表示开课，false表示结束)(服务端发出)
 */
public class PulpitStatusInfo extends MUCCommandInfo implements Serializable {

    private static final long serialVersionUID = 195077406630916026L;
    
    public static Integer TYPE = 4;

    /**
     * 命令类型 default 4
     */
    private  int commondType = TYPE;

    /**
     * 是否禁言
     * true表示开课，false表示结束
     */
    private boolean value;

    public int getCommondType() {
        return commondType;
    }

    public void setCommondType(int commondType) {
        this.commondType = commondType;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
