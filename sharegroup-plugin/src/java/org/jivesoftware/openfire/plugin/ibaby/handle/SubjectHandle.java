package org.jivesoftware.openfire.plugin.ibaby.handle;

import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserManager;
import org.xmpp.packet.Packet;

/**
 * Created by Admin on 2016/3/22.
 * 抽象状态类，定义一个接口以封装与PacketContent的一个特定状态相关的行为
 */
public abstract class SubjectHandle {

    private PresenceManager presenceManager;

    private UserManager userManager;

    public SubjectHandle() {
    }

    public SubjectHandle(PresenceManager presenceManager, UserManager userManager) {
        this.presenceManager = presenceManager;
        this.userManager = userManager;
    }

    public PresenceManager getPresenceManager() {
        return presenceManager;
    }

    public void setPresenceManager(PresenceManager presenceManager) {
        this.presenceManager = presenceManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public abstract void handle(Packet packet, Session session) throws PacketRejectedException;

}
