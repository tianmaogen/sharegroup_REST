package com.sharegroup.rest.openfire;

import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.service.ChatManager;
import com.sharegroup.rest.service.MUCManager;
import com.sharegroup.rest.service.UserManager;
import org.jivesoftware.smack.AbstractXMPPConnection;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2015/12/3.
 */
public class SmackManager {

        /**
         * The Date Formatter to use in Spark.
         */
        private static final String dateFormat = ((SimpleDateFormat)SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.FULL,SimpleDateFormat.MEDIUM)).toPattern();
        public static final SimpleDateFormat DATE_SECOND_FORMATTER = new SimpleDateFormat(dateFormat);

        //session manager
        private static SmackSessionManager sessionManager;
        //user manager
        private static UserManager userManager;
        //one to one chat message manager
        private static ChatManager chatManager;
        //chat room manager
        private static MUCManager mucManager;


        private SmackManager() {
            // Do not allow initialization
        }

        /**
         * Gets the {@link SmackSessionManager} instance.
         *
         * @return the SessionManager instance.
         */
        public static SmackSessionManager getSessionManager() {
            //初始化sessionManager
            if (sessionManager == null || sessionManager.getConnection() == null) {
                AuthenticationToken token = new AuthenticationToken(RestSetting.AUTH_USERNAME,RestSetting.AUTH_PASSWORD);
                sessionManager = new SmackSessionManager(token);
            }
            return sessionManager;
        }



        /**
         * Gets the {@link AbstractXMPPConnection} instance.
         * this method must login
         * @return the {@link AbstractXMPPConnection} associated with this session.
         */
        public static AbstractXMPPConnection getConnection() {
            return sessionManager.getConnection();
        }

        /**
         * Returns the <code>UserManager</code> for LiveAssistant. The UserManager
         * keeps track of all users in current chats.
         *
         * @return the <code>UserManager</code> for LiveAssistant.
         */
        public static UserManager getUserManager() {
            if (userManager == null) {
                userManager = new UserManager();
            }
            return userManager;
        }

    /**
         * Returns the ChatManager. The ChatManager is responsible for creation and removal of
         * chat rooms, transcripts, and transfers and room invitations.
         *
         * @return the <code>ChatManager</code> for this instance.
         */
        public static ChatManager getChatManager() {
            if (chatManager == null) {
                chatManager = new ChatManager();
            }
            return chatManager;
        }

    /**
     * get muc manager
     * @return
     */
    public static MUCManager getMucManager(String token) {
        if(mucManager == null){
            mucManager  = new MUCManager(token);
        }
        return mucManager;
    }

    /*public static boolean userSessionCheck() {
        //判断管理员是否登录，如果登录则直接发送消息
        String jid = SmackManager.getSessionManager().getJID();

        boolean isauth = false;
        if(jid == null){
            AuthenticationToken auth  = new AuthenticationToken(RestSetting.AUTH_USERNAME,RestSetting.AUTH_PASSWORD);
            try {
                SmackManager.getUserManager().userLogin(auth);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException e) {
                e.printStackTrace();
            }
            isauth = true;
        }else {
            isauth = false;
        }
        return isauth;
    }*/

    /**
     *  验证用户是否存在
     *      存在返回true
     *       不存在返回 false
     * @return
     */
    /*public static boolean validateMember(groupMessage groupMessage,Affiliation type){

        boolean flag = false;

        AuthenticationToken auth = new AuthenticationToken(groupMessage.getManagerName(), groupMessage.getManagerPwd());

        AbstractXMPPConnection connection = SmackManager.getSessionManager().getConnection(auth);

        try {
            connection.connect();
            connection.login();
            LogUtils.WriteInfo("MUC : " + connection.getUser() + " 用户登录成功 ");
        } catch (SmackException e) {
            LogUtils.WriteError("MUC error:",e);
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.WriteError("MUC error:",e);
            e.printStackTrace();
        } catch (XMPPException e) {
            LogUtils.WriteError("MUC error:",e);
            e.printStackTrace();
        }

        try {
            //验证消息
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            String room = MUC_JID.getRoomJID(groupMessage.getRoomId());

            //验证讲坛
            MultiUserChat userChat = manager.getMultiUserChat(room);
            if(userChat == null || !room.equals(userChat.getRoom())) {
                LogUtils.WriteInfo("该讲坛不存在.{}",room);
                return false;
            }
            //进入聊天室
            userChat.join(groupMessage.getManagerName());
            *//**
             * 验证用户是否为管理员
             *//*
            if(Affiliation.admin  == type ) {
                List<Affiliate> admins = userChat.getAdmins();
                for (Affiliate aff : admins) {
                    if (groupMessage.getManagerName().equals(aff.getNick())) {
                        flag = true;
                        break;
                    }
                }
            }else if(Affiliation.outcast == type){

            }else{
                //获取在线用户
                List<Occupant> list = userChat.getParticipants();
                if(list != null && list.size() > 0){
                    for(Occupant o : list){
                        if (groupMessage.getManagerName().equals(o.getNick())) {
                            flag = true;
                        }
                    }
                }
            }
            LogUtils.WriteInfo("MUC 用户验证通过");
        }catch (SmackException e) {
            LogUtils.WriteError("MUC error:",e);
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return flag;
    }*/
}
