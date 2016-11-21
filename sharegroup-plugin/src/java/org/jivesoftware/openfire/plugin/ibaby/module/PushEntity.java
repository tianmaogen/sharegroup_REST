package org.jivesoftware.openfire.plugin.ibaby.module;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 * 推送消息接口
 */
public class PushEntity {


    //用户ID
    private String userkey;

    //推送内容
    private String content;

    private static Gson g = new Gson();


    @Override
    public String toString() {
        StringBuffer sbf = new StringBuffer("?");

        sbf.append("userkey=\"" + this.userkey + "\"");
        sbf.append("&content=\"" + this.content+"\"");
        return sbf.toString();
    }


    /*
     * 消息类型:
     *  免费问诊消息  1
     *  新的聊天消息  2
     *  系统消息  7
     *
     */
    public static PushEntity PushTemplate(String toJID, ClientChatMessage message) {
        PushEntity pe = new PushEntity();
        int ind = toJID.indexOf("@");
        String userKey = toJID.substring(0, ind);
        pe.setUserkey(userKey);

        PushEntityItem item = new PushEntityItem();
        item.setMessagetype(PushEntityItem.NEW_CHAT_MESSAGE);

        item.setMessageId(message.getClientMsgId());
        item.setXGts(message.getCreated());
        item.setAPSAlert(message.getObjectName() + " 给您发了消息");
        pe.setContent(g.toJson(item));
        return pe;
    }

    /*
     * 消息类型:
     *  免费问诊消息  1
     *  新的聊天消息  2
     *  系统消息  7
     *
     */
    public static PushEntity PushTemplate(String toJID, CommandMessage message) {

        boolean flag = false;
        PushEntityItem item = new PushEntityItem();
        String xgTs = new String(String.valueOf(System.currentTimeMillis()));
        /* 系统消息：102
         * 评论消息:103
         * 免费问诊消息：104
         * 关闭问诊: 105
         */
        switch (message.getCacheType()) {
            case 101:
                break;
            case 102:
                item.setMessagetype(PushEntityItem.NEW_SYSTEM_MESSAGE);
                //item.setMessageId(message.getCacheValue());
                item.setXGts(xgTs);
                item.setAPSAlert(message.getCacheTitle());
                flag = true;
                break;
            case 103:
                break;
            case 104:
                item.setMessagetype(PushEntityItem.NEW_QUESTION_MESSAGE);
                item.setMessageId(message.getCacheValue());
                item.setXGts(xgTs);
                item.setAPSAlert(message.getCacheTitle());
                flag = true;
                break;
            case 105:
                break;
            default:

        }
        if (flag) {

            PushEntity pe = new PushEntity();
            int ind = toJID.indexOf("@");
            if (ind > 0) {
                String userKey = toJID.substring(0, ind);
                pe.setUserkey(userKey);
            } else {
                pe.setUserkey(toJID);
            }

            pe.setContent(g.toJson(item));
            return pe;

        } else {
            return null;
        }
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
