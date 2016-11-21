package org.jivesoftware.openfire.plugin.ibaby.handle;

import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Packet;

/**
 * Created by Admin on 2016/3/22.
 * 封装message状态类
 */
public class PacketContent {

    private SubjectHandle packetState;

    public PacketContent(SubjectHandle packetState) {
        this.packetState = packetState;
    }

    public SubjectHandle getPacketState() {
        return packetState;
    }

    public void setPacketState(SubjectHandle packetState) {
        this.packetState = packetState;
    }

    /**
     * 对请求做处理
     */

    public void doHandle(Packet packet, Session session) throws PacketRejectedException {


        if(null != packetState)
        packetState.handle(packet, session);

    }
}
