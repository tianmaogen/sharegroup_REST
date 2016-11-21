package org.jivesoftware.openfire.plugin.ibaby.handle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.plugin.ibaby.module.*;
import org.jivesoftware.openfire.plugin.ibaby.service.DotNETMessageService;
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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 2016/3/22.
 * 消息类型处理
 */
public class ChatMessageState extends SubjectHandle {

    private static final Logger log = LoggerFactory.getLogger(ChatMessageState.class);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ChatMessageState(PresenceManager presenceManager, UserManager userManager) {
        super(presenceManager, userManager);
    }

    @Override
    public void handle(Packet packet, Session session) throws PacketRejectedException {
        Message message = (Message) packet;
        log.info("message : " + packet);
        //subject[message,command,groupCmd]
        String subject = message.getSubject();
        String chatbody = message.getBody();
        //为空不做处理
        if (null == chatbody) {
            log.info("chatbody is null" + packet);
            throw new PacketRejectedException("消息格式不正确");
        }
        /**
         * 接收到消息，对消息进行以下处理
         * 1.对消息拦截验证
         * 2.返回验证结果
         * 3.修改聊天时间为服务器时间，为了统一所有用户的消息时间及顺序
         * 4.同步聊天消息到.NET服务器
         */
        try {
            //修改客户端聊天时间，统一使用服务器时间
            ClientChatMessage clientmsg = gson.fromJson(chatbody, ClientChatMessage.class);
            //contentType = 99 ，结束符，不做验证
            if ("99".equals(clientmsg.getContentType())) {
                log.info("content typ eq 99 ,do nothing");
                return;
            }

            String created = sdf.format(new Date());
            clientmsg.setCreated(created);
            //兼容以前版本 20151218
            clientmsg.setToId(message.getTo().getNode());
            //对消息进行逻辑验证，通过.NET进行业务处理
            CheckChatEntity chatEntity = new CheckChatEntity();
            chatEntity.setChatType(clientmsg.getChatType());
            chatEntity.setToUser(message.getTo().getNode());
            chatEntity.setFromUser(clientmsg.getObjectId());
            chatEntity.setDiagnosisId(clientmsg.getChatMainId());

            ResultMessageNET rmsg = DotNETMessageService.requestDotNETAPI(chatEntity);
            if (rmsg != null && rmsg.getSuccess() && rmsg.getCode() == 200) {
                //同步消息到.NET服务器
                DotNETMessageService.pushToDotNET(clientmsg);

                //修改后的数据重写到body中，进行消息分发
                message.setBody(gson.toJson(clientmsg));

                //回执发送端 消息封装
                ReplyMessage replyMessage = new ReplyMessage();
                replyMessage.setCode(200);
                replyMessage.setCreated(clientmsg.getCreated());
                replyMessage.setMsg("消息发送成功");
                replyMessage.setSuccess(true);

                //消息回执
                Message reply = message.createCopy();
                reply.setFrom(message.getTo());
                reply.setTo(message.getFrom());
                reply.setSubject("response");
                reply.setBody(replyMessage.toString());
                if (session != null) {
                    session.process(reply);
                }
                //消息下发前，检查用户是否在线
                try {
                    JID recipient = message.getTo();
                    User u = getUserManager().getUser(recipient.getNode());
                    Presence cpresence = getPresenceManager().getPresence(u);
                    if (cpresence == null) { //用户不在线，走离线推送消息，并消息下发
                        PushEntity2 pushEntity = PushMessageService.AssemblePushEntity2(message.createCopy());
                        PushMessageService.pushToClient(pushEntity);
                    } else {
                    }
                } catch (Exception ex) {
                    log.info("IbabyPlugin user validate exception : " + ex.getMessage());
                }

            } else {
                //定义 201为服务器验证消息不通过
                Message reply = message.createCopy();
                reply.setFrom(message.getTo());
                reply.setTo(message.getFrom());
                ReplyMessage replyMessage = new ReplyMessage();
                replyMessage.setCode(201);
                replyMessage.setCreated(clientmsg.getCreated());
                replyMessage.setMsg(rmsg.getMsg());
                replyMessage.setSuccess(false);

                reply.setSubject("response");
                reply.setBody(replyMessage.toString());
                session.process(reply);
                log.info("message not send ,reason is :" + rmsg);
                throw new PacketRejectedException("服务器验证不通过:" + rmsg.getMsg());
            }
        } catch (Exception ex) {
            throw new PacketRejectedException("ibaby PacketRejectedException :" + ex.getMessage());
        } finally {
            //do nothing
        }
    }
}
