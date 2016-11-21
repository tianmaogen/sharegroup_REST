package org.jivesoftware.openfire.plugin.ibaby.service;

import com.google.gson.Gson;
import org.jivesoftware.openfire.OfflineMessageStore;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.ibaby.module.ClientChatMessage;
import org.jivesoftware.openfire.plugin.ibaby.module.PushEntity2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.PacketExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-18
 * Time: 下午8:03
 * To change this template use File | Settings | File Templates.
 * 消息暂存，
 * 0，收到Message则存储到本队列中，分用户存存储
 * 1，收到客户端发送的接收确认指令，则移除消息
 * 2，收到监听服务发送的客户端掉线指令，发送该用户的所有消息到offline
 */
public class OnlineMessageQueue {

    private static final Logger Log = LoggerFactory.getLogger(OnlineMessageQueue.class);

    private static OnlineMessageQueue instance = new OnlineMessageQueue();

    private static OfflineMessageStore offlineMessageStore;

    /**
     * 待发送到客户端的消息队列
     */

    private static Map<String, Map<String, Packet>> packets = new HashMap<String, Map<String, Packet>>();

    private static Gson g;


    public static OnlineMessageQueue getInstance() {
        g = new Gson();
        offlineMessageStore = XMPPServer.getInstance().getOfflineMessageStore();
        return instance;
    }

    /**
     * 存储用户发送的消息,以已方为key 存储
     *
     * @param packet
     */
    public static void add(Packet packet) {
        Log.info("Quque add:" + packet);

        Message message = (Message) packet;
        String subject = message.getSubject();
        String toJID = message.getTo().getNode();
        //离线消息分离
        PacketExtension extension = packet.getExtension("delay", "urn:xmpp:delay");
        //处理用户消息对象
        if ("message".equals(subject) && extension == null) {
            ClientChatMessage msg = g.fromJson(message.getBody(), ClientChatMessage.class);
            /**
             * 如果用户存在，则增加用户消息进队列
             * 如果用户不存在，则增加一个用户信息并保存消息对像
             */

            if (packets.containsKey(toJID)) {
                packets.get(toJID).put(packet.getID(), packet.createCopy());
            } else {
                Map<String, Packet> pk = new HashMap<String, Packet>();
                pk.put(packet.getID(), packet.createCopy());
                packets.put(toJID, pk);
            }
        } else if ("command".equals(subject)) {
            //do nothing
        } else {

        }
    }

    /**
     * 删除客户端已经接收到的消息
     *
     * @param packet
     */
    public static void remove(Packet packet) {
        Log.info("Quque remove:" + packet);
//        System.out.println("Quque remove:" + packet);
        Message message = (Message) packet;
        String subject = message.getSubject();
        String fromJID = message.getFrom().getNode();
        //处理用户消息对象
        if ("received".equals(subject)) {
            String messageID = packet.getID();
            if (packets.containsKey(fromJID)) {
                packets.get(fromJID).remove(messageID);
            } else {
                //do nothing
            }
        } else if ("command".equals(subject)) {
            //do nothing
        } else {

        }
    }

    public static void storeOffline(String JID) throws IOException {
        //处理用户消息对象
        if (packets.containsKey(JID)) {
            Log.info("Quque store:" + JID);
            Map<String, Packet> messageMap = packets.get(JID);

            for (Map.Entry<String, Packet> entry : messageMap.entrySet()) {
                Message storemsg = (Message) entry.getValue();
                //离线消息存储
                offlineMessageStore.addMessage(storemsg.createCopy());
                /**
                 *  2016-03-22 Jade 增加的消息类型，不做消推送
                 */
                String subject = storemsg.getSubject();
                if("comp".equals(subject)){
                    continue;
                }
                //推送消息简要组装
                PushEntity2 pushEntity = PushMessageService.AssemblePushEntity2(storemsg.createCopy());
                PushMessageService.pushToClient(pushEntity);

            }
            packets.remove(JID);

        } else {
        }
    }

}
