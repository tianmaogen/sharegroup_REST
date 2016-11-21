package org.jivesoftware.openfire.plugin.ibaby.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.openfire.plugin.ibaby.module.*;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-1-13
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 * 验证讲坛状态，是否禁言
 */
public class GroupMessageService {


    private static Logger log = LoggerFactory.getLogger(GroupMessageService.class);

    static String SERVER_HOST = JiveGlobals.getProperty("ibaby.pulpitserver.host");
    static String SERVER_VAL_URL = JiveGlobals.getProperty("ibaby.pulpitvalserver.method");
    static String SERVER_PUSH_URL = JiveGlobals.getProperty("ibaby.pulpitpushserver.method");

    /**
     * 是否可以发言 验证
     * @param roomId 验证的讲坛
     * @param userNick 要发言的用户
     * @return  true 则可以发言 false 则为禁止发言
     * @throws IOException
     */
    public static String isParticipant(String roomId,String userNick) {
         CloseableHttpClient httpclient = HttpClients.createDefault();
         String returnMsg = null;
        try {
            Gson gson = new Gson();
            GroupEntity groupEntity = new GroupEntity(roomId,userNick);
            HttpPost httppost = new HttpPost(SERVER_HOST + SERVER_VAL_URL+groupEntity.toString());

            log.info("GROUP VAL:" + roomId+" -- > " +userNick);
            /*StringEntity reqEntity = new StringEntity(,"UTF-8");
            reqEntity.setContentEncoding("charset=UTF-8");
            reqEntity.setContentType("application/json");
            httppost.setEntity(reqEntity);
            System.out.println(httppost.getURI());*/

            CloseableHttpResponse response = httpclient.execute(httppost);
            //HttpEntity entity  =  response.getEntity();
            String responseStr = EntityUtils.toString(response.getEntity());
            log.info("GROUP down:" + responseStr);
//            System.out.println("GROUP down:" + responseStr);
            ResultMessageNET resultMessage = gson.fromJson(responseStr,ResultMessageNET.class);
            if( true == resultMessage.getSuccess() && true == (Boolean)resultMessage.getData())
                returnMsg = "true";
            else
                returnMsg = resultMessage.getMsg();
        } catch (Exception ex) {
            returnMsg = ex.getMessage();
           log.error(ex.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return returnMsg;
    }

    /**
     * 同步聊天消息到讲坛
     * @param messageBody
     */
    public static void pushToPulpit(String messageBody) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(SERVER_HOST + SERVER_PUSH_URL);
            Gson g= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            PulpitMessage msg = g.fromJson(messageBody,PulpitMessage.class);
            //msg.setPulpitId(msg.getChatMainId());

            String str = g.toJson(msg);
            log.info("SYNC(PULPIT) start:" + str);

            StringEntity reqEntity = new StringEntity(str,"UTF-8");
            reqEntity.setContentEncoding("charset=UTF-8");
            reqEntity.setContentType("application/json");


            httppost.setEntity(reqEntity);
            System.out.println(httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            log.info("SYNC(PULPIT) end:" + EntityUtils.toString(response.getEntity()));
//            System.out.println("SYNC(PULPIT) end:" + EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }


}
