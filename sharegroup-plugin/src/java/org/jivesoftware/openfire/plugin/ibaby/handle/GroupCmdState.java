package org.jivesoftware.openfire.plugin.ibaby.handle;

import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

/**
 * Created by Admin on 2016/3/22.
 * 群透传命脉类型处理
 */
public class GroupCmdState extends SubjectHandle {

    public GroupCmdState() {
    }

    @Override
    public void handle(Packet packet, Session session) throws PacketRejectedException {

        groupCmdMessage((Message) packet);
    }

    private void groupCmdMessage(Message message) throws PacketRejectedException {
        try {

        } catch (Exception ex) {
            throw new PacketRejectedException("ibaby PacketRejectedException :" + ex.getMessage());
        } finally {
            //do nothing
        }
    }
}
