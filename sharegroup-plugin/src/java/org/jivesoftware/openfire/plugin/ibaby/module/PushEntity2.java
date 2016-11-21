package org.jivesoftware.openfire.plugin.ibaby.module;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-16
 * Time: 下午7:09
 * To change this template use File | Settings | File Templates.
 */
public class PushEntity2 {


    /**
     * 免费问诊消息  1
     * 新的聊天消息  2
     * 系统消息  7
     * 讲坛消息 8
     */
    public static String NEW_QUESTION_MESSAGE = "1";
    public static String NEW_CHAT_MESSAGE = "2";
    public static String NEW_SYSTEM_MESSAGE = "7";
    public static String NEW_PULPIT_MESSAGE = "8";


    //用户ID
    private String userkey;

    //推送内容
    private String content;

    //聊天会话id
    private String messageId;

    /**
     * 推送消息类型
     * 1：新的免费问诊消息（只有医生端会有）
     * 2：新的聊天消息
     * 7：系统消息
     */
    private String messageType;


    /**
     *聊天类型
     1表示免费问诊
     2表示图文问诊
     3表示主管医生
     4表示好友聊天
     */

    private String chatType;

    /*
    * 消息类型:
    *  免费问诊消息  1
    *  新的聊天消息  2
    *  系统消息  7
    *
    */
    public static PushEntity2 PushTemplate(String toJID, ClientChatMessage message) {
        PushEntity2 p2 =  new PushEntity2();

        int ind = toJID.indexOf("@");
        if (ind > 0) {
            String userKey = toJID.substring(0, ind);
            p2.setUserkey(userKey);
        } else {
            p2.setUserkey(toJID);
        }

        p2.setChatType(message.getChatType());
        p2.setContent(message.getObjectName() +" 给您发了消息");
        p2.setMessageType(PushEntity2.NEW_CHAT_MESSAGE);
        p2.setMessageId(message.getChatMainId());

        return p2;
    }
    /*
    * 消息类型:
    *  免费问诊消息  1
    *  新的聊天消息  2
    *  系统消息  7
    *
    */
    public static PushEntity2 PushTemplate(String toJID, CommandMessage message) {
        PushEntity2 p2 =  new PushEntity2();
        boolean flag = false;

       /* 系统消息：102
        * 评论消息:103
        * 免费问诊消息：104
        * 关闭问诊: 105
        */
        switch (message.getCacheType()) {
            case 101:
                break;
            case 102:
                p2.setMessageType(PushEntity2.NEW_SYSTEM_MESSAGE);
                flag = true;
                break;
            case 103:
                break;
            case 104:
                p2.setMessageType(PushEntity2.NEW_QUESTION_MESSAGE);
                flag = true;
                break;
            case 105:
                break;
            case 106:
                p2.setMessageType(PushEntity2.NEW_PULPIT_MESSAGE);
                flag=true;
                break;
            default:

        }

        if (flag) {

            int ind = toJID.indexOf("@");
            if (ind > 0) {
                String userKey = toJID.substring(0, ind);
                p2.setUserkey(userKey);
            } else {
                p2.setUserkey(toJID);
            }

            p2.setContent(message.getCacheTitle());
            p2.setMessageId(message.getCacheValue());

            return p2;

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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
}
