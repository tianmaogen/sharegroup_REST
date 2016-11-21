package com.sharegroup.test;

import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.utils.LogUtils;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.Occupant;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on15/12/30.
 */
public class MUCChatTest2 {

    static String userName = "ibaby";
    static String password = "b59ae791092ed8fe036ed870c2caa4d950e41ee71d9f0e3f";

    public static void main(String[] args) {

        //int classID = 1;
        String roomName = "16051314225523523146";


        try {
            AbstractXMPPConnection connection =  connectToService();
            connection.login();
            //获取房间管理对象
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
//            manager.getJoinedRooms();
            System.out.println(connection.getUser());

            MultiUserChat muc = manager.getMultiUserChat(roomName+"@conference.192.168.1.202");
            muc.join(userName);

            System.out.println("chat room is : "+muc.getRoom());
            System.out.println("getOccupantsCount:"+muc.getOccupantsCount());
            List<Occupant> list = muc.getParticipants();
            System.out.println("getParticipants:"+list.size());
            /*List<Affiliate> admin = muc.getAdmins();
            System.out.println("ADMIN");
            for(Affiliate af : admin){
                System.out.println("jid:"+af.getJid()+" | affi:"+af.getAffiliation()+" | role:"+af.getRole());
            }*/
            System.out.println("Occupant");
            boolean flag = false;
            String user = "wendy";
            if(list != null && list.size() > 0){
                for(Occupant o : list){
                    System.out.println("jid:"+o.getJid()+" | affi:"+o.getAffiliation()+" | role:"+o.getRole());
                }
            }

            /**
             *
                 <message to="ibaby1@conference.pwgzl8wcrkhmt45/jade" id="kg0JF-17" type="chat" from="admin@pwgzl8wcrkhmt45/Smack">
                 <body>上课时间，禁止说话</body>
                 </message>
             */
            //muc.sendMessage("bbbbb");

           /* Message message = new Message();
            String  nickName = "613582213d2147f7ac5ebb82cf6a3e17";
            message.setFrom(connection.getUser());
            message.setBody("上课时间，禁止说话");
            message.setType(Message.Type.groupchat);
            message.setTo(muc.getRoom()+"/"+nickName);
            message.setSubject("message");
            connection.sendStanza(message);*/

            //muc.sendMessage(message);
            //muc.pollMessage();


        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getRoomJID(String user){
        return  new String(user+"@conference.pwgzl8wcrkhmt45");
    }

    public static String getJID(String user){
        return  new String(user+"@pwgzl8wcrkhmt45/Spark");
    }


    public static AbstractXMPPConnection connectToService() {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setServiceName(RestSetting.SERVICE_NAME)
                .setHost(RestSetting.SERVICE_HOST)
                .setPort(RestSetting.SERVICE_PORT)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(RestSetting.SERVICE_ISDEBUG)
                .setUsernameAndPassword(userName,password)
                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);
        try {

            connection.connect();

        } catch (SmackException e) {
            LogUtils.WriteError(null, e);
        } catch (IOException e) {
            LogUtils.WriteError(null, e);
        } catch (XMPPException e) {
            LogUtils.WriteError(null, e);
        }

        return connection;
    }
}
