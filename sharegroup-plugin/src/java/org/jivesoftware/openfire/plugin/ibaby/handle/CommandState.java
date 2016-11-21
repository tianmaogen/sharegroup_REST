package org.jivesoftware.openfire.plugin.ibaby.handle;

import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.plugin.ibaby.module.PushEntity2;
import org.jivesoftware.openfire.plugin.ibaby.service.PushMessageService;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

/**
 * Created by Admin on 2016/3/22.
 * 透传命脉类型处理
 */
public class CommandState extends SubjectHandle {

    public CommandState(PresenceManager presenceManager, UserManager userManager) {
        super(presenceManager, userManager);
    }

    private static final Logger log = LoggerFactory.getLogger(CommandState.class);

    @Override
    public void handle(Packet packet, Session session) throws PacketRejectedException {
        Message message = (Message) packet;
        //消息下发前，检查用户是否在线
        try {
            JID recipient = message.getTo();
            User u = getUserManager().getUser(recipient.getNode());
            Presence cpresence = getPresenceManager().getPresence(u);
            if (cpresence == null) { //用户不在线，走离线推送消息，并消息下发
                PushEntity2 pushEntity = PushMessageService.AssemblePushEntity2(message.createCopy());
                PushMessageService.pushToClient(pushEntity);
            } else {
                //do nothing
            }

        } catch (Exception ex) {
            throw new PacketRejectedException("ibaby PacketRejectedException :" + ex.getMessage());
        } finally {
            //do nothing
        }
    }

}
