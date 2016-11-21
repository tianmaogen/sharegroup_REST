package com.sharegroup.rest.utils;

import com.sharegroup.rest.openfire.RestSetting;

/**
 * Created by Administrator on 2016/1/13.
 */
public class MUC_JID {
    /**
     * 房间名称
     * @param roomId
     * @return
     */
    public static String getRoomJID(String roomId){
        StringBuffer sbf = new StringBuffer(roomId).append("@conference.").append(RestSetting.SERVICE_NAME);
        return  sbf.toString();
    }

    /**
     * 用户JID ,不带resource地址
     * @param user
     * @return
     */
    public static String getJID(String user){
        StringBuffer sbf = new StringBuffer(user).append("@").append(RestSetting.SERVICE_NAME);
        return  sbf.toString();
    }

}
