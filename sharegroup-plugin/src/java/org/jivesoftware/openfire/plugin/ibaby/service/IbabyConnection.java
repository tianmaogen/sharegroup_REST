package org.jivesoftware.openfire.plugin.ibaby.service;

import org.apache.mina.core.session.IoSession;
import org.jivesoftware.openfire.Connection;
import org.jivesoftware.openfire.ConnectionCloseListener;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.nio.NIOConnection;
import org.jivesoftware.openfire.session.LocalSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.cert.Certificate;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-1-13
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public class IbabyConnection implements ConnectionCloseListener {

    private static final Logger Log = LoggerFactory.getLogger(IbabyConnection.class);

    private LocalSession session;

    public void close(boolean peerIsKnownToBeDisconnected) {
        /**
         * 存储用户消息到队列中，要等待客户端回执后才能进行删除，
         * Jade  20151218
         * rows 7
         */
        try {

            JID jid = session.getAddress();
            if(jid != null){
                OnlineMessageQueue.getInstance().storeOffline(jid.getNode());

            }
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }

    @Override
    public void onConnectionClose(Object handback) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println(handback);
    }
}
