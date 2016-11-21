package org.jivesoftware.openfire.plugin.ibaby.service;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.openfire.plugin.ibaby.module.ClientChatMessage;
import org.jivesoftware.openfire.plugin.ibaby.module.CommandMessage;
import org.jivesoftware.openfire.plugin.ibaby.module.PushEntity;
import org.jivesoftware.openfire.plugin.ibaby.module.PushEntity2;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 * 验证用户无法及时接收到消息，则走推送通道，把消息推送给用户
 */
public class PushMessageService {


    private static Logger log = LoggerFactory.getLogger(PushMessageService.class);
    private static Gson g = new Gson();


    public static void pushToClient(PushEntity2 pushEntity) throws IOException {

        String str = g.toJson(pushEntity);
        pushMessage(str);
    }

    public static void pushToClient(PushEntity pushEntity) throws IOException {

        String str = g.toJson(pushEntity);
        pushMessage(str);
    }

    private static void pushMessage(String  pushStr) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String SERVER_HOST = JiveGlobals.getProperty("ibaby.pushserver.host");
            String SERVER_PUSH_URL = JiveGlobals.getProperty("ibaby.pushserver.method");
            HttpPost httppost = new HttpPost(SERVER_HOST + SERVER_PUSH_URL);

            log.info("PUSH start :" + pushStr);
//            System.out.println("PUSH start :" + pushStr);
            StringEntity reqEntity = new StringEntity(pushStr,"UTF-8");
            reqEntity.setContentEncoding("charset=UTF-8");
            reqEntity.setContentType("application/json");


            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            log.info("PUSH down:" + EntityUtils.toString(response.getEntity()));
//            System.out.println("PUSH down:" + EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
           log.error(ex.getMessage());
        } finally {
            httpclient.close();
        }
    }

    /**
     * 封装推送消息
     * 消息体内容为全部内容
     * @param message
     * @return
     */
    public static PushEntity AssemblePushEntity(Message message){
        Gson gson = new Gson();
        PushEntity pushEntity = null;
        if("message".equals(message.getSubject())){

            ClientChatMessage clientmsg = gson.fromJson( message.getBody(),ClientChatMessage.class);
            pushEntity = PushEntity.PushTemplate(message.getTo().toBareJID(), clientmsg);
        }else if("command".equals(message.getSubject())){

            CommandMessage commandMessage =  gson.fromJson( message.getBody(),CommandMessage.class);
            pushEntity = PushEntity.PushTemplate(message.getTo().toBareJID(),commandMessage);
        }
        return pushEntity;
    }

    /**
     * 封装推送消息
     * 消息体为简要内容
     * @param message
     * @return
     */
    public static PushEntity2 AssemblePushEntity2(Message message){
        Gson gson = new Gson();
        PushEntity2 pushEntity = null;

        if("message".equals(message.getSubject())){

            ClientChatMessage clientmsg = gson.fromJson( message.getBody(),ClientChatMessage.class);
            pushEntity = PushEntity2.PushTemplate(message.getTo().toBareJID(), clientmsg);
        }else if("command".equals(message.getSubject())){

            CommandMessage commandMessage =  gson.fromJson( message.getBody(),CommandMessage.class);
            pushEntity = PushEntity2.PushTemplate(message.getTo().toBareJID(),commandMessage);
        }
        return pushEntity;
    }
}
