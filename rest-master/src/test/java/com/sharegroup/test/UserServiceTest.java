package com.sharegroup.test;

import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.model.chatMessage;
import com.sharegroup.rest.openfire.SmackManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/3.
 */
public class UserServiceTest {

    /**
     * 测试用户登录
     */
    @Test
    public void userLoginTest(){
        AuthenticationToken auth = new AuthenticationToken("0c36a7c77a3947c8a62fc9a43deeb003","A30A7A1C6CBDEC53");
        try {
            SmackManager.getUserManager().userLogin(auth);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        String ss = SmackManager.getSessionManager().getUserNick();
        System.out.println(ss);
    }

    /**
     * 用户发送消息
     */
    @Test
    public void userChat(){
        userLoginTest();
        chatMessage msg = new chatMessage();
        msg.setToUser("lucy");
        msg.setMessage("hi lucy.i'm ibaby");
        try {
            SmackManager.getChatManager().sentMessage(msg);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 匿名登录测试
     */
    /*@Test
    public void AnonymouslyLogin(){
        String msgStr = "{\"ObjectId\" : \"c6544f4637ed45ef8dd61eb1a1098bee\",  \"Content\" : \"凡此等等\",  \"ClientMsgId\" : \"8D3193EFFD0D400E922FC6CF80EA1DF9\",  \"ExpandJson\" : \"\",  \"ContentType\" : 10,  \"Created\" : \"2015-12-11 14:33:35\",  \"ObjectName\" : \"杨磊\",  \"ContentUrl\" : \"\",  \"ObjectType\" : 2,  \"ChatMainId\" : \"f525aeaaa28a415c89306c130673543f\",  \"ChatType\" : 3,  \"ObjectHead\" : \"2015\\/06\\/17\\/638b1237-1ff4-440a-ab5b-9df9f1b8d243.png\"}";

        try {
            SmackManager.getUserManager().anonymouslyLogin();
            String ss = SmackManager.getSessionManager().getJID();
            System.out.println(ss);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }

        chatMessage msg = new chatMessage();
        msg.setToUser("lucy");
        msg.setMessage(msgStr);
        try {
            SmackManager.getChatManager().sentMessage(msg);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }*/
}
