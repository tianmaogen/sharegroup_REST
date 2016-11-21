package com.red5.ibaby.pulpit;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 * 验证用户无法及时接收到消息，则走推送通道，把消息推送给用户
 */
public class SyncUserService {


    private static Logger log = LoggerFactory.getLogger(SyncUserService.class);

    public static void pushMessage(String url , PulpitUser user) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            log.info("red5 start SYNC USER :" + user);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pulpitId", user.getPulpitId()));
            params.add(new BasicNameValuePair("userId", user.getUserId()));
            params.add(new BasicNameValuePair("state", user.getState()+""));
            httppost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpclient.execute(httppost);
            System.out.println("red5 end SYNC USER :" + EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
           log.error(ex.getMessage());
        } finally {
            httpclient.close();
        }
    }

    /*public static void main(String [] args){
        String pulpit_user_notify = "http://192.168.1.58:88/pulpitSystem/syncPulpitUserOnline";
        //String msg = SyncUserService.AssemblePushEntity("a","b",1);
        try {
            PulpitUser pulpitUser = new PulpitUser("a","b",1);
            SyncUserService.pushMessage(pulpit_user_notify,pulpitUser);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //pushMessage(pulpit_user_notify) ;
    }*/
}
