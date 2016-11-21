package org.jivesoftware.openfire.plugin.ibaby.handle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.plugin.ibaby.module.PulpitMessage;
import org.jivesoftware.openfire.plugin.ibaby.module.ReplyMessage;
import org.jivesoftware.openfire.plugin.ibaby.service.GroupMessageService;
import org.jivesoftware.openfire.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 2016/3/22.
 * 消息类型处理
 */
public class MUCMessageState extends SubjectHandle {

    private static final Logger log = LoggerFactory.getLogger(MUCMessageState.class);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MUCMessageState() {
    }

    @Override
    public void handle(Packet packet, Session session) throws PacketRejectedException {
        Message message = (Message) packet;
        log.info("message : " + packet);
        String chatbody = message.getBody();
        //为空不做处理
        if (null == chatbody) {
            log.info("chatbody is null" + packet);
            throw new PacketRejectedException("消息格式不正确");
        }

        try {
            //是否禁言，验证
            String roomId = message.getTo().getNode();
            String user = message.getFrom().getNode();
            String msg = GroupMessageService.isParticipant(roomId, user);
            if (null != msg && !"true".equals(msg)) {
                //定义 202 为禁言                                        `
                Message reply = message.createCopy();
                reply.setFrom(message.getTo());
                reply.setTo(message.getFrom());

                ReplyMessage replyMessage = new ReplyMessage();
                replyMessage.setCode(202);
                replyMessage.setCreated(sdf.format(new Date()));
                replyMessage.setMsg(msg);
                replyMessage.setSuccess(false);

                reply.setSubject("response");
                reply.setBody(replyMessage.toString());
                session.process(reply);
                log.info("message not send ,reason isParticipant :" + msg);
                throw new PacketRejectedException("服务器验证不通过：" + msg);
            }
            //统一修改服务器时间
            PulpitMessage pulpitMessage = gson.fromJson(chatbody, PulpitMessage.class);
            String created = sdf.format(new Date());
            try {
                pulpitMessage.setCreated(sdf.parse(created));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //修改后的数据重写到body中，进行消息分发
            message.setBody(gson.toJson(pulpitMessage));
            //同步消息到讲坛
            GroupMessageService.pushToPulpit(message.getBody());


            //回执发送端 消息封装
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(200);
            replyMessage.setCreated(sdf.format(pulpitMessage.getCreated()));
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
        } catch (Exception ex) {
            throw new PacketRejectedException("ibaby PacketRejectedException :" + ex.getMessage());
        } finally {
            //do nothing
        }
    }
}
