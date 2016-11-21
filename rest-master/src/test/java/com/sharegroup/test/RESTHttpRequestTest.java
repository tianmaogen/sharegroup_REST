package com.sharegroup.test;

import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/10.
 */
public class RESTHttpRequestTest {

    private static String SERVER_HOST = "http://pwgzl8wcrkhmt45:81";


    @Test
    public void httpGETRequestTest(){
        String rquestParam = "/userservice/login";
//        HttpClient httpClient = new HttpClient();
//        //创建方法实例
//        HttpMethod get = new GetMethod(SERVER_HOST+rquestParam);
//        //执行get方法
//        try {
//            httpClient.executeMethod(get);
//            //获取返回的页面内容
//            String content = get.getResponseBodyAsString();
//            //释放链接资源
//            get.releaseConnection();
//
//            System.out.println(content);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void httpPOSTRequestTest(){
        String SERVER_URL = "/chatservice/chatmessage/jade";
//        HttpClient httpClient = new HttpClient();
//
//        //创建方法实例
//        PostMethod postMethod = new PostMethod (SERVER_HOST+SERVER_URL);

//        NameValuePair nameValuePair0 = new NameValuePair("chatType","1");
//        NameValuePair nameValuePair1 = new NameValuePair("noteName","note");
//        //将参数放入post方法中去
//        postMethod.setRequestBody(new NameValuePair[] { nameValuePair0,nameValuePair1} );

//        ChatEntity entity = new ChatEntity();
//        entity.setChatType("1");
//        entity.setNoteName("note");
//        postMethod.setRequestEntity(entity);
//
//        postMethod.addRequestHeader("Content-Type" , "application/json;charset=UTF-8");
//        postMethod.addRequestHeader("token","myApiKeyXXXX123456789");
//
//        //执行get方法
//        try {
//
//            //服务器返回的页面内容出现中文乱码，可以设置如下参数：
//            httpClient.getParams().setParameter(HttpMethodParams.HTTP_URI_CHARSET, "UTF-8");
//
//            //连接超时设置
//            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//            //读取信息超时设置
//            httpClient.getHttpConnectionManager().getParams().setSoTimeout(1000);
//
//            httpClient.executeMethod(postMethod);
//
//            System.out.println(JSON.toJSONString(httpClient.getParams()));
//
//            //获取返回的页面内容
//            String content = postMethod.getResponseBodyAsString();
//            //释放链接资源
//            postMethod.releaseConnection();
//
//            System.out.println(content);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void testPost() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://192.168.1.222:8080/pushServer/push/pushMessage");

            // It may be more appropriate to use FileEntity class in this particular
            // instance but we are using a more generic InputStreamEntity to demonstrate
            // the capability to stream out data from any arbitrary source
            //

            //List<NameValuePair> params = Form.form().add("chatType", "1").add("toUser", "jade").add("fromUser", "ibaby").build();
//            String str = "chatType=1&toUser=111&fromUser=1111";
//            ByteArrayEntity reqEntity = new ByteArrayEntity(str.getBytes("UTF-8"));
//            httppost.setEntity(reqEntity);

            System.out.println("Executing request: " + httppost.getRequestLine());
            String str = "userkey=\"1f7fd795ee2345dea07cca7ef2b06557\"&content=\"系统消息\"";
//            List<NameValuePair> params = Form.form().add("userkey", "1f7fd795ee2345dea07cca7ef2b06557").add("content", "系统消息").build();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("userkey","1f7fd795ee2345dea07cca7ef2b06557");
            jsonParam.put("content","系统消息");
            StringEntity reqEntity = new StringEntity(jsonParam.toString(),"UTF-8");
            reqEntity.setContentEncoding("charset=UTF-8");
            reqEntity.setContentType("application/json");
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            System.out.println(httppost.getURI());
            System.out.println(httppost.getEntity());

            try {
                Gson gson = new Gson();
//                ResultMessageNET resultMessage = gson.fromJson(EntityUtils.toString(response.getEntity()),ResultMessageNET.class);
                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                System.out.println(EntityUtils.toString(response.getEntity()));
//                System.out.println(gson.toJson(resultMessage));

                System.out.println(EntityUtils.toString(response.getEntity()));

            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }


}
