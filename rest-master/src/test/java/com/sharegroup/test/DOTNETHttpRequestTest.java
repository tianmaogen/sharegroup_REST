package com.sharegroup.test;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class DOTNETHttpRequestTest {

    private static String SERVER_HOST = "http://192.168.1.201:30016/";



    @Test
    public void httpGETRequestTest(){
        String rquestParam = "api/system/Chat/CheckChat?chatType=1&toUser=111&fromUser=1111";

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
    public void httpGETTest(){
        String rquestParam = "api/system/Chat/CheckChat?chatType=1&toUser=111&fromUser=1111";

        try {
            String result = Request.Get(SERVER_HOST + rquestParam)
                    .connectTimeout(5000)
                    .socketTimeout(5000)
                    .execute().returnContent().asString();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static String SERVER_CHAT_URL = "api/system/Chat/CheckChat";

    @Test
    public void httpPOSTRequestTest(){
        String rquestParam = "api/system/Chat/CheckChat?chatType=1&toUser=111&fromUser=1111";
          /*
           * 1-免费问诊 2-图文问诊 3-医患聊天 4-好友聊天
           */
            //chatType=1&toUser=111&fromUser=111
            List<NameValuePair> params = Form.form().add("chatType", "1").add("toUser", "jade").add("fromUser", "ibaby").build();

            try {

                String result = Request.Post(SERVER_HOST + rquestParam)
                        .connectTimeout(5000)
                        .socketTimeout(5000)
                        .bodyForm(params)
                        .execute().returnContent().asString();

                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

//        String SERVER_URL = "api/system/Chat/CheckChat";
//        HttpClient httpClient = new HttpClient();
//        //创建方法实例
//        PostMethod postMethod = new PostMethod (SERVER_HOST+SERVER_URL);
//
//        NameValuePair nameValuePair0 = new NameValuePair("chatType","1");
//        NameValuePair nameValuePair1 = new NameValuePair("User","11");
//        NameValuePair nameValuePair2 = new NameValuePair("fromUser","1111");
//        //将参数放入post方法中去
//        postMethod.setRequestBody(new NameValuePair[] { nameValuePair0,nameValuePair1,nameValuePair2} );
//
//
//        String rquestParam = "api/system/Chat/CheckChat?chatType=1&toUser=111&fromUser=1111";
//        PostMethod post = new PostMethod(SERVER_HOST+rquestParam);
//
//
//        //执行get方法
//        try {
//           // httpClient.executeMethod(postMethod);
//            httpClient.executeMethod(post);
//
//            //服务器返回的页面内容出现中文乱码，可以设置如下参数：
//            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//            //连接超时设置
//            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//            //读取信息超时设置
//            httpClient.getHttpConnectionManager().getParams().setSoTimeout(1000);
//
//
////            httpClient.executeMethod(post);
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
//
//    }



}
