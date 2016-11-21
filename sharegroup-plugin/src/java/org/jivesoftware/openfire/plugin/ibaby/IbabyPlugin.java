package org.jivesoftware.openfire.plugin.ibaby;

import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.plugin.ibaby.handle.*;
import org.jivesoftware.openfire.plugin.ibaby.service.OnlineMessageQueue;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-29
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class IbabyPlugin implements Plugin, PacketInterceptor {

    private static final Logger log = LoggerFactory.getLogger(IbabyPlugin.class);

    private static PluginManager pluginManager;

    private PresenceManager presenceManager;

    private UserManager userManager;

    private InterceptorManager interceptoerManager;


    public IbabyPlugin() {
        XMPPServer server = XMPPServer.getInstance();
        presenceManager = server.getPresenceManager();
        userManager = server.getUserManager();
        interceptoerManager = InterceptorManager.getInstance();
    }


    /**
     * 拦截消息，在消息进行分发前处理
     *      1，消息合法性验证
     *      2，消息内容修改（eg.统一消息时间为服务器时间等）
     * @param packet the packet to take action on.
     * @param session the session that received or is sending the packet.
     * @param incoming flag that indicates if the packet was read by the server or sent from
     *      the server.
     * @param processed flag that indicates if the action (read/send) was performed. (PRE vs. POST).
     * @throws PacketRejectedException
     */
    @Override
    public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {

        //消息下发前拦截
        if(incoming && !processed && packet instanceof Message ){
            System.out.println("validate Message ......");
            Message message = (Message)packet;
            //subject[message,command,groupCmd,comp]
            String subject = message.getSubject();

            SubjectHandle packetState = null;
            if(Message.Type.chat ==  message.getType()){
                if ("message".equals(subject)) {
                    packetState = new ChatMessageState(presenceManager,userManager);
                }else if("command".equals(subject)){
                    packetState = new CommandState(presenceManager,userManager);
                }else if("comp".equals(subject)){
                    //packetState = new CompState();
                }else if("groupCmd".equals(subject)){
                    //packetState = new GroupCmdState();
                }else{
                    //do nothing
                    //throw new PacketRejectedException("消息格式不正确");
                }
            }else if(Message.Type.groupchat ==  message.getType()){
                if ("message".equals(subject)) {
                    packetState = new MUCMessageState();
                }else if("groupCmd".equals(subject)){
                    //packetState = new GroupCmdState();
                }else{
                    //do nothing
                    // throw new PacketRejectedException("消息格式不正确");
                }
            }

            if(null != packetState ) {
                //消息有业务需要处理的，则通过doHandle去完成
                new PacketContent(packetState).doHandle(packet, session);
            }
            System.out.println("validate end ...........");
        }

        /*
        * one to one
        * 服务器处理后，消息发送 ,
        * 并保存消息到队列中，要收到客户端的回执消息后才能确认消息已经成功发送
        */
        if(!incoming && processed && packet instanceof Message && ((Message) packet).getType() == Message.Type.chat){

            messageToQueue(packet);
        }

        /**
         * 监控用户在线状态
         */
        if(packet instanceof Presence){
            UserUnavaliable(packet);
        }

    }


    /**
     * 存储用户消息到队列中，要等待客户端回执后才能进行删除，
     * Jade  20151218
     * rows 10
     */
    private void messageToQueue(Packet packet) throws PacketRejectedException {

        Message message = (Message) packet;
        if(Message.Type.chat ==  message.getType()){
            System.out.println("message add queue............");
            String subject = message.getSubject();
            /*
             * 2016-03-22 Jade 增加需要加入队列的消息类型comp
            */
            if("message".equals(subject) || "comp".equals(subject)){
                OnlineMessageQueue.getInstance().add(packet);
            }else if("received".equals(subject)){
                OnlineMessageQueue.getInstance().remove(packet);
                throw new PacketRejectedException("收到回执，不做处理");
            }else{}
        }
    }

    /**
     * 监控用户状态
     * @param packet
     */
    private void UserUnavaliable(Packet packet) {
        Presence presence = (Presence) packet;
        /**
         * 掉线后服务器主动发送的掉线通知包
         */
        if(Presence.Type.unavailable == presence.getType()){
            log.info("USER UNAVAILABLE :" + packet);
            /**
             * 处理队列中的消息
             */
            try {
                OnlineMessageQueue.getInstance().storeOffline(presence.getFrom().getNode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        log.info("Ibaby Plugin init");
        //To change body of implemented methods use File | Settings | File Templates.
        pluginManager = manager;
        interceptoerManager.addInterceptor(this);
    }

    @Override
    public void destroyPlugin() {
        log.info("Ibaby Plugin destroy");
        //To change body of implemented methods use File | Settings | File Templates.

        interceptoerManager.removeInterceptor(this);
    }


}
