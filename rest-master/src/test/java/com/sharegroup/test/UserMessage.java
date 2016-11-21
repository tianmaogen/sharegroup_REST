package com.sharegroup.test;

import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.utils.LogUtils;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

import java.io.IOException;

/**
 * Created by Admin on 2016/4/29.
 */
public class UserMessage {

    static String userName = "admin";
    static String password = "1234";
    static String userName2 = "ibaby";
    static String password2 = "b59ae791092ed8fe036ed870c2caa4d950e41ee71d9f0e3f";
    static String userName3= "21fe15fe8edc4302a49b7fe28b21a72a";
    static String password3 = "A30A7A1C6CBDEC53";

    public static void main(String [] args){

        AbstractXMPPConnection connection = connectToService(userName,password);


        try {
            connection.login();

            String roomName = "16051611005892055149";
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            System.out.println(connection.getUser());

            MultiUserChat muc = manager.getMultiUserChat(roomName+"@conference.192.168.1.202");
            muc.join(userName);

            muc.addMessageListener(new myMessageListener());
            muc.addParticipantListener(new PresenceListener() {
                public void processPresence(Presence presence) {
                    System.out.println("processPresence");
                    System.out.println(presence);
                }
            });

            System.out.println("system.wait");
            System.in.read();

        } catch (XMPPException e) {
            e.printStackTrace();
            System.out.println("XMPPException");
        } catch (SmackException e) {
            e.printStackTrace();
            System.out.println("SmackException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }

       /* AbstractXMPPConnection connection2 = connectToService(userName2,password2);


        try {
            connection2.login();

            String roomName = "16042917020765667306";
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection2);
            System.out.println(connection.getUser());

            MultiUserChat muc = manager.getMultiUserChat("16042916253417040466"+"@conference.192.168.1.202");
            muc.join(userName);

            muc.addMessageListener(new myMessageListener());
            muc.addParticipantListener(new PresenceListener() {
                public void processPresence(Presence presence) {
                    System.out.println("processPresence2");
                    System.out.println(presence);
                }
            });

            System.out.println("system.wait");
            System.in.read();

        } catch (XMPPException e) {
            e.printStackTrace();
            System.out.println("XMPPException");
        } catch (SmackException e) {
            e.printStackTrace();
            System.out.println("SmackException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }*/



//        org.jivesoftware.smack.chat.ChatManager chatmanager = org.jivesoftware.smack.chat.ChatManager.getInstanceFor(connection);

        // chatmanager.addChatListener((ChatManagerListener) new myMessageListener());

    }

    public static AbstractXMPPConnection connectToService(String userName,String password) {
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

class myMessageListener implements MessageListener {

    public void processMessage(Message message) {
        System.out.println("message");
        System.out.println(message);
    }
}
