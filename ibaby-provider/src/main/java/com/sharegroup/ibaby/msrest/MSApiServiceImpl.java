package com.sharegroup.ibaby.msrest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sharegroup.ibaby.Module.ResultMessageNET;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/2/16.
 */
public class MSApiServiceImpl implements MSApiService {


    private static Logger log = LoggerFactory.getLogger(MSApiServiceImpl.class);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    static String SERVER_HOST = JiveGlobals.getProperty("ibaby.netserver.host"); // http://192.168.1.201:30016/

    static String SERVER_GETPWD_URL = JiveGlobals.getProperty("ibaby.netuserpwd.method"); // api/system/User/getPassword?username={username}

    static String SERVER_GETUSER_URL = JiveGlobals.getProperty("ibaby.netuserload.method"); // api/system/User/loadUser?username={username}

    static String SERVER_GETCOUNT_URL = JiveGlobals.getProperty("ibaby.netusercount.method"); // api/system/User/getUserCount

    static String SERVER_GETPAGE_URL = JiveGlobals.getProperty("ibaby.netuserpage.method"); // api/system/User/getUsers?startIndex={startIndex}&numResults={numResults}



    public MSApiServiceImpl(){
    }

    @Override
    public String getPassword(String username) throws UserNotFoundException {
        if(username == null) {
            throw new UserNotFoundException(username);
        }

        ResultMessageNET resultMessage = null;
        String uri = new String(SERVER_HOST+SERVER_GETPWD_URL+"?username="+username);
        try {
            //获取数据
            resultMessage = getInfoFromNET(uri);
            //验证数据
            if(resultMessage.getSuccess() == true && resultMessage.getCode() == 200){
                return (String) resultMessage.getData();
            }else{
                log.error("get password ",resultMessage);
                throw new UserNotFoundException(username);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("get password ",e);
            throw new UserNotFoundException(username);
        }


    }

    @Override
    public User loadUser(String username) throws UserNotFoundException {
        if(username == null) {
            throw new UserNotFoundException(username);
        }
        ResultMessageNET resultMessage = null;
        String uri = new String(SERVER_HOST+SERVER_GETUSER_URL+"?username="+username);
        User user = null;
        try {
            //获取数据
            resultMessage = getInfoFromNET(uri);
            //验证数据
            if(resultMessage.getSuccess() == true && resultMessage.getCode() == 200 && resultMessage.getData() != null){
                String jsonStr = gson.toJson(resultMessage.getData());
                user = gson.fromJson(jsonStr,User.class);
                //user = gson.fromJson(resultMessage.getData().toString(),User.class);
                log.info("load user object " + gson.toJson(user));
            }else{
                log.error("load user "+resultMessage);
                throw new UserNotFoundException(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("load user error ",e);
            throw new UserNotFoundException(username);
        }
        return user;
    }

    @Override
    public int getUserCount() {
        ResultMessageNET resultMessage = null;
        String uri = new String(SERVER_HOST+SERVER_GETCOUNT_URL);
        int usercount = 0;
        try {
            //获取数据
            resultMessage = getInfoFromNET(uri);
            //验证数据
            if(resultMessage.getSuccess() == true && resultMessage.getCode() == 200 ){
                if(resultMessage.getData() instanceof  Integer) {
                    usercount =  (Integer)resultMessage.getData();
                }else if(resultMessage.getData() instanceof  Double){
                    Double dc = (Double)resultMessage.getData();
                    usercount =  dc.intValue();
                }
            }
        } catch (IOException e) {
            log.error("get user count ",e);
        }
        return usercount;
    }

    @Override
    public List<String> getUsers(int startIndex, int numResults) {
        log.info("get users ");
        ResultMessageNET resultMessage = null;
        String uri = new String(SERVER_HOST+SERVER_GETPAGE_URL+"?startIndex="+startIndex+"&numResults="+numResults);
        List<String> userNames = new ArrayList<String>();
        try {
            //获取数据
            resultMessage = getInfoFromNET(uri);
            //验证数据
            if(resultMessage.getSuccess() == true && resultMessage.getCode() == 200 && resultMessage.getData() != null){
                String jsonStr = gson.toJson(resultMessage.getData());
                log.info("users str  : "+resultMessage.getData().toString());
                userNames = gson.fromJson(jsonStr,List.class);
            }else{
                log.info("users is null",resultMessage.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("get users ",e);
        }
        return userNames;
    }

    @Override
    public List<String> findUsers(Set<String> fields, int startIndex, int numResults) {

        return null;
    }


    /**
     * 获取用户数据
     * @param uri 请求地址及参数
     * @return
     * @throws IOException
     */
    private ResultMessageNET getInfoFromNET(String uri) throws IOException {
        log.info("get user Info :"+uri);
        ResultMessageNET resultMessage;CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = httpclient.execute(httpGet);
        String responseStr = EntityUtils.toString(response.getEntity());

        resultMessage  = gson.fromJson(responseStr,ResultMessageNET.class);

        httpclient.close();
        return resultMessage;
    }

}
