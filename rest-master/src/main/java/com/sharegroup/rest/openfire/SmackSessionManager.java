package com.sharegroup.rest.openfire;

import com.sharegroup.rest.bean.AuthenticationToken;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/3.
 */
public class SmackSessionManager implements ConnectionListener {


    private AbstractXMPPConnection connection;

    private String serverName;

    private String serverHost;

    private String userNick;

    /**
     * 创建客户端连接
     * @param auth
     */
    public SmackSessionManager(AuthenticationToken auth) {

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(auth.getUsername(), auth.getPassword())
                .setServiceName(RestSetting.SERVICE_NAME)
                .setHost(RestSetting.SERVICE_HOST)
                .setPort(RestSetting.SERVICE_PORT)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(RestSetting.SERVICE_ISDEBUG)
                .build();

        AbstractXMPPConnection conn = new XMPPTCPConnection(config);

        try {
            //添加监听
            conn.addConnectionListener(this);
            //用默认账户登录
            conn.connect().login();
            //设置常用属性
            connection = conn;
            setUserNick(connection.getUser());
            setServerName(connection.getServiceName());
            setServerHost(connection.getHost());

        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取链接
     * @return
     */
    public AbstractXMPPConnection getConnection() {
        return connection;
    }

    /**
     * 登录
     */
    public void login(){

    }

    /**
     * 重新连接
     */
    public void reConnection() {
        System.out.println("reconnection");
        if(!connection.isConnected())
            try {
                connection.connect();
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
            System.out.println("connection  is connected");
    }

    /**
     * 是否连接还存在
     * @return
     */
    public boolean isConnection(){

        return this.connection.isConnected();
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



    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
}
