package com.sharegroup.test;

import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.openfire.RestSetting;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/2/1.
 */
public class xmpppConnectionTest implements ConnectionListener {

    static String user = "ibaby";

    static String pwd = "b59ae791092ed8fe036ed870c2caa4d950e41ee71d9f0e3f";

    static AbstractXMPPConnection connection;
    static messageManager manager;

    public static void main(String[] args){


        String nick = "jade";
        try {
            xmpppConnectionTest xmpptest = new xmpppConnectionTest();
                     xmpptest.anonymouslyLogin(user, pwd);

            System.out.println(connection.getUser() + " is wait");
            manager = new messageManager(connection);

            String JID = new String(nick+"@"+connection.getHost());
            Long sl = null;
            Long el = null;
            while(true){
                sl = System.currentTimeMillis();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String inputStr = br.readLine();
                if("close".equals(inputStr))
                    break;
                else if(null == inputStr || "".equals(inputStr))
                    continue;

                if(!connection.isConnected())
                    connection.connect();
                manager.sendMessage(JID, inputStr);
            }
            el = System.currentTimeMillis();
            System.out.println("close....");
            System.out.println(el-sl);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户匿名登录
     * @throws IOException
     * @throws XMPPException
     * @throws SmackException
     */
    public AbstractXMPPConnection anonymouslyLogin(String user ,String pwd) throws IOException, XMPPException, SmackException {

        AuthenticationToken auth = new AuthenticationToken(user,pwd);

        AbstractXMPPConnection connection = getConnection(auth);
        connection.connect().login();

        connection.addConnectionListener(this);
        this.connection = connection;
        return connection;
    }

    public void resetConnection(String user,String pwd){
        try {
            System.out.println(connection);
            if(!connection.isConnected()) {
                connection.connect();
                System.out.println("connect .........");
                //anonymouslyLogin(user, pwd);
            }
            //manager = new messageManager(connection);
            System.out.println(" init manager.. ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }

    }

    /**
     * 登录并获取连接
     * @return
     */
    public AbstractXMPPConnection getConnection(AuthenticationToken auth) {

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(auth.getUsername(), auth.getPassword())
                .setServiceName(RestSetting.SERVICE_NAME)
                .setHost(RestSetting.SERVICE_HOST)
                .setPort(RestSetting.SERVICE_PORT)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(RestSetting.SERVICE_ISDEBUG)
                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);
        return connection;

    }

    public void connected(XMPPConnection xmppConnection) {
        System.out.println("REST connected :"+xmppConnection.getUser());
    }

    public void authenticated(XMPPConnection xmppConnection, boolean b) {
        System.out.println("REST authenticated :"+b);
    }

    public void connectionClosed() {
        System.out.println("REST connection closed");
    }

    public void connectionClosedOnError(Exception e) {
        System.out.println("REST close on Error :"+e.getMessage());
        e.printStackTrace();
    }

    public void reconnectionSuccessful() {
        System.out.println("REST reconnection successful");
    }

    public void reconnectingIn(int i) {
        System.out.println("REST reconnection in :"+i);
    }

    public void reconnectionFailed(Exception e) {
        System.out.println("REST reconnection failed "+e.getMessage());
        e.printStackTrace();
    }

    static class messageManager implements ChatManagerListener{

        AbstractXMPPConnection connection;

        public messageManager(AbstractXMPPConnection conn) {
            this.connection = conn;
        }

        public void sendMessage(String jid, String msg){
            System.out.println(jid + " -----> " +msg );
            //会话管理者的建立和配置监听
            org.jivesoftware.smack.chat.ChatManager chatmanager = org.jivesoftware.smack.chat.ChatManager.getInstanceFor(connection);
            chatmanager.addChatListener(this);

            //建立会话
            Chat chat = chatmanager.createChat(jid);
            chat.getThreadID();

            //发消息
            Message packet = new Message();
            packet.setBody(msg);

            try {
                chat.sendMessage(packet);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }

        }


        public void chatCreated(Chat chat, boolean b) {

            System.out.println("chat Created :" + b);

            chat.addMessageListener(new ChatMessageListener() {

                public void processMessage(Chat cc, Message mm) {
                    System.out.println("chat : "+ cc);
                    System.out.println("process message : "+mm);
                }
            });
        }
    }

}
