package org.jivesoftware.openfire.plugin.ibaby.service;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.openfire.plugin.ibaby.module.CheckChatEntity;
import org.jivesoftware.openfire.plugin.ibaby.module.ClientChatMessage;
import org.jivesoftware.openfire.plugin.ibaby.module.ResultMessageNET;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 * 拦截消息，对用户A与用户B的聊天合法性进行验证
 * 调用.NET验证消息接口
 */
public class DotNETMessageService {

    private static Logger log = LoggerFactory.getLogger(DotNETMessageService.class);

    static String SERVER_HOST = JiveGlobals.getProperty("ibaby.netserver.host");
    static String SERVER_CHAT_URL = JiveGlobals.getProperty("ibaby.netserver.method");

    public static ResultMessageNET requestDotNETAPI(CheckChatEntity chatEntity){
          /*
           * 1-免费问诊 2-图文问诊 3-医患聊天 4-好友聊天
           */
        CloseableHttpClient httpclient = null;
        try{
            httpclient = HttpClients.createDefault();
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        ResultMessageNET resultMessage = null;
        try {
            HttpPost httppost = new HttpPost(SERVER_HOST + SERVER_CHAT_URL + chatEntity.toString());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                Gson gson = new Gson();
                log.info("VAL(.NET) start:"+gson.toJson(chatEntity));
                String responseStr = EntityUtils.toString(response.getEntity());
                resultMessage  = gson.fromJson(responseStr,ResultMessageNET.class);
                log.info("VAL(.NET) end:" + responseStr);
            } finally {
                response.close();
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return resultMessage;
      }

    /**
     * 同步聊天消息到.NET
     * @param msg
     */
    public static void pushToDotNET(ClientChatMessage msg) {

        String SERVER_PUSH_URL = "api/system/Chat/SyncChatLog";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(SERVER_HOST + SERVER_PUSH_URL);
            Gson g = new Gson();
            String str = g.toJson(msg);
            log.info("SYNC(.NET) start:" + str);
            StringEntity reqEntity = new StringEntity(str,"UTF-8");
            reqEntity.setContentEncoding("charset=UTF-8");
            reqEntity.setContentType("application/json");


            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            log.info("SYNC(.NET) end:" + EntityUtils.toString(response.getEntity()));
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
