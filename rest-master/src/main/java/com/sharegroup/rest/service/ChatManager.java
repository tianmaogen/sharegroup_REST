package com.sharegroup.rest.service;

import com.google.gson.Gson;
import com.sharegroup.rest.bean.RequestChatMessage;
import com.sharegroup.rest.bean.RequestMUCCommand;
import com.sharegroup.rest.model.MUCCommandBody;
import com.sharegroup.rest.model.chatMessage;
import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.openfire.SmackManager;
import com.sharegroup.rest.utils.LogUtils;
import com.sharegroup.rest.utils.MUC_JID;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.Occupant;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
public class ChatManager implements ChatManagerListener {

    Gson g = new Gson();


    public void sentMessage(chatMessage message) throws SmackException.NotConnectedException {
        sentMessage(message,"message");
    }


    /**
     * 单消息
     * @param message
     * @param style
     * @throws SmackException.NotConnectedException
     */
    public void sentMessage(chatMessage message,String style) throws SmackException.NotConnectedException {


        if(!SmackManager.getSessionManager().isConnection()) {
            SmackManager.getSessionManager().reConnection();
        }
        AbstractXMPPConnection connection = SmackManager.getConnection();
        //会话管理者的建立和配置监听
        org.jivesoftware.smack.chat.ChatManager chatmanager = org.jivesoftware.smack.chat.ChatManager.getInstanceFor(connection);
        chatmanager.addChatListener(this);

        String userJID  = message.getToUser()+"@"+connection.getServiceName();

        LogUtils.WriteInfo(style.toUpperCase()+" : "+message.getMessage());

        //建立会话
        Chat chat = chatmanager.createChat(userJID);
        chat.getThreadID();

        //发消息
        Message msg = new Message();
        msg.setBody(message.getMessage());

        if("message".equals(style)){

            RequestChatMessage cmsg = g.fromJson(message.getMessage(),RequestChatMessage.class);
            msg.setPacketID(cmsg.getClientMsgId());
        }else if("command".equals(style)){
            //do nothing
        }

        msg.setSubject(style);

        chat.sendMessage(msg);
        chat.close();
    }

    /**
     * 群消息
     * @param command 用户消息，
     * @param style 命令类型
     * @param type  消息类型
     *            groupchat：群发
     *            chat 群私聊,单发
     * @throws SmackException.NotConnectedException
     */
    public String sentMUCMessage(RequestMUCCommand command , String style,Message.Type type) {

        /**
         * 群管理员登录，向群用户发送消息
         */
        if(!SmackManager.getSessionManager().isConnection()) {
            SmackManager.getSessionManager().reConnection();
        }
        AbstractXMPPConnection connection = SmackManager.getConnection();

        try {
            //验证消息
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            String room = MUC_JID.getRoomJID(command.getRoomId());

            //验证讲坛
            MultiUserChat userChat = manager.getMultiUserChat(room);
            //进入聊天室

            //String userNick = SmackManager.getSessionManager().getUserNick();
            //进入聊天室发送消息
            userChat.join(RestSetting.AUTH_USERNAME);

           /* //进入聊天室发送消息
            userChat.join(groupMessage.getManagerName());*/

            //发送消息

            Message message = new Message();
            message.setFrom(connection.getUser());
            message.setSubject(style);
            //命令发送，指定id这讲坛尖
            if("groupCmd".equals(style)) {
                message.setPacketID(command.getRoomId());
            }

            if(Message.Type.groupchat == type){ //命令群发
                message.setTo(room);
                message.setType(type);
            }else if(Message.Type.chat == type){ //命令单发
                //TODO 管理课件，消息只有管理者与讲师之间发送，并且消息体不带用户参数
                String userId = new String(command.getUserId());
                if("1".equals(command.getCommondType())){
                    command.setUserId(null);
                }
                message.setTo(room + "/" + userId);
                message.setType(Message.Type.chat);
            }else{}

            /**
             * 透传消息
             */
            MUCCommandBody body = new MUCCommandBody();
            body.setUserId(command.getUserId());
            body.setUserName(command.getUserName());
            body.setCommondType(command.getCommondType()+"");
            body.setValue(command.getValue());

            message.setBody(g.toJson(body));

            System.out.println(message);
            connection.sendStanza(message);
            LogUtils.WriteInfo("MUC message", message);
            return "success";

        } catch (SmackException e) {
            LogUtils.WriteError("MUC error:", e);
            e.printStackTrace();
        } catch (XMPPException e) {
            LogUtils.WriteError("MUC error:", e);
            e.printStackTrace();
        }/* catch (IOException e) {
            LogUtils.WriteError("MUC error:", e);
            e.printStackTrace();
        }*/ finally {
            //connection.disconnect();
        }
        return "end muc";
    }

    /**
     * 统计讲坛中的用户数量
     * @Jade 2016-04-27
     * @param roomId
     * @return
     */
    public List<Occupant> getMUCUser(String roomId) {

        /**
         * 群管理员登录，向群用户发送消息
         */
        if(!SmackManager.getSessionManager().isConnection()) {
            SmackManager.getSessionManager().reConnection();
        }
        AbstractXMPPConnection connection = SmackManager.getConnection();

        try {
            //验证消息
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            String room = MUC_JID.getRoomJID(roomId);

            //验证讲坛
            MultiUserChat userChat = manager.getMultiUserChat(room);
            //进入聊天室
            userChat.join(RestSetting.AUTH_USERNAME);
            userChat.nextMessage();
            //统计数据
            //List<Occupant> list = userChat.getParticipants();
            //return userChat.getOccupantsCount();
            return  userChat.getParticipants();
        }catch (Exception ex){
            LogUtils.WriteError("get user count ",ex);
        }
        return null;
    }

    /*public void setMessageTinder(chatMessage message){

        boolean isauth = SmackManager.userSessionCheck();
        // Assume we've created an XMPPConnection name "connection".
        AbstractXMPPConnection connection ;
        if(isauth) {
            connection = SmackManager.getConnection();
        }else{
            return;
        }

        com.sharegroup.rest.packet.Message packet = new com.sharegroup.rest.packet.Message();
        packet.setType(com.sharegroup.rest.packet.Message.Type.chat);
        packet.setTo(message.getToUser()+"@"+connection.getServiceName());
        packet.setFrom(SmackManager.getSessionManager().getJID());
        packet.setBody(message.getMessage());

        SmackManager.getConnection().sendPacket(packet);

    }*/

    public void chatCreated(Chat chat, boolean b) {
        //当收到来自对方的消息时触发监听函数
        chat.addMessageListener(new ChatMessageListener() {

            public void processMessage(Chat cc, Message mm) {
                LogUtils.WriteInfo("receive:" + mm);
                System.out.println(mm);
            }
        });

    }
}
