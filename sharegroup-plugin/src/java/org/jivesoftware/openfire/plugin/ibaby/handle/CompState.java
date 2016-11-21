package org.jivesoftware.openfire.plugin.ibaby.handle;

import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

/**
 * Created by Admin on 2016/3/22.
 * 订阅消息类型处理
 */
public class CompState extends SubjectHandle {

    public CompState() {
    }

    @Override
    public void handle(Packet packet, Session session) throws PacketRejectedException {
        compMessage((Message) packet);
    }

    private void compMessage(Message message) throws PacketRejectedException {
        try {

        } catch (Exception ex) {
            throw new PacketRejectedException("ibaby PacketRejectedException :" + ex.getMessage());
        } finally {
            //do nothing
        }
    }
}
