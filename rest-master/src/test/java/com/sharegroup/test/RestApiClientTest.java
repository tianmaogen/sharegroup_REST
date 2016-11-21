package com.sharegroup.test;

import com.google.gson.Gson;
import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.bean.ResultMessageNET;
import com.sharegroup.rest.packet.RestApiClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.openfire.plugin.rest.entity.UserEntity;

import javax.ws.rs.core.Response;
import java.io.IOException;

public class RestApiClientTest {


    public static void main(String [] args){

        //testREQuest();
        testOpenfire();

    }



    private static void testOpenfire() {
        // Set Shared secret key
        //localhost : 2RRO7i13GqNryj3a
        //linux 222 : XRbghp7zHw8Yx6gX
        AuthenticationToken authenticationToken = new AuthenticationToken("J582abl7whmE0HLo");
//        AuthenticationToken authenticationToken = new AuthenticationToken("admin", "admin");
        // Set Openfire settings (9090 is the port of Openfire Admin Console)
        //192.168.1.222  127.0.0.1
        RestApiClient restApiClient = new RestApiClient("192.168.1.58", 9090, authenticationToken);

        UserEntity user = restApiClient.getUser("spark1");

        if(user!= null){
            user.setPassword("1EC966ACD9F331E4");
            Response r = restApiClient.updateUser(user);

            System.out.println(r.getStatus());
        }else{
            System.out.println("未找到用户");
        }
    }


}