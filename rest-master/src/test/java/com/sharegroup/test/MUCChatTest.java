package com.sharegroup.test;

import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.packet.RestApiClient;
import com.sharegroup.rest.utils.LogUtils;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on15/12/30.
 * 创建讲坛群
 * roomId & name is unique
 */
public class MUCChatTest {

    static int classID = 1;
    static String roomId = "pulpit"+classID;
    static String name = "ibaby_plupit_class"+classID;
    static String desc = "this is ibaby pulpit";
    static String nick = "admin";


    public static void main(String[] args) {
        //createMUC();
        managementMUC();
    }

    private static void createMUC() {

        try {
            AbstractXMPPConnection connection =  connectToService();
            connection.login();
            //获取房间管理对象
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            //创建一个房间
            System.out.println(connection.getUser());
            MultiUserChat muc = manager.getMultiUserChat(roomId+"@conference.pwgzl8wcrkhmt45");
            muc.create(nick);
            System.out.println(muc.getRoom());
            // User1 (which is the room owner) configures the room as a moderated room
            Form form = muc.getConfigurationForm();

            Form answerForm = form.createAnswerForm();
            //向提交的表单添加默认答复,获取房间的默认设置菜单
            for (FormField field : form.getFields()) {
                if (!FormField.Type.hidden.name().equals(field.getType()) && field.getVariable() != null) {
                    answerForm.setDefaultAnswer(field.getVariable());
                }
            }

            //muc#
            //房间名称
            answerForm.setAnswer(FormField.FORM_TYPE, "http://jabber.org/protocol/muc#roomconfig");
            //设置房间名称
            answerForm.setAnswer("muc#roomconfig_roomname", name);
            //设置房间描述
            answerForm.setAnswer("muc#roomconfig_roomdesc", desc);
            //是否允许修改主题
            answerForm.setAnswer("muc#roomconfig_changesubject", true);

            //设置房间最大用户数
            List<String> maxusers = new ArrayList<String>();
            maxusers.add("999");
            answerForm.setAnswer("muc#roomconfig_maxusers", maxusers);


            List<String> cast_values = new ArrayList<String>();
            cast_values.add("moderator");
            cast_values.add("participant");
            cast_values.add("visitor");
            answerForm.setAnswer("muc#roomconfig_presencebroadcast", cast_values);
            //设置为公共房间
            answerForm.setAnswer("muc#roomconfig_publicroom", true);
            //设置为永久房间
            answerForm.setAnswer("muc#roomconfig_persistentroom", true);
            //允许修改昵称
            answerForm.setAnswer("x-muc#roomconfig_canchangenick", true);
            //允许用户登录注册房间
            answerForm.setAnswer("x-muc#roomconfig_registration", true);


            muc.sendConfigurationForm(answerForm);
            muc.join(nick);

        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void managementMUC() {
        String userName = "jade";
        try {
            AbstractXMPPConnection connection = connectToService();
            connection.login();
            //获取房间管理对象
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            //创建一个房间
            System.out.println(connection.getUser());
            MultiUserChat muc = manager.getMultiUserChat(roomId + "@conference.pwgzl8wcrkhmt45");
            muc.create(nick);
            // User1 (which is the room owner) configures the room as a moderated room
            Form form = muc.getConfigurationForm();
            Form answerForm = form.createAnswerForm();
            answerForm.setAnswer("muc#roomconfig_moderatedroom", "1");
            muc.sendConfigurationForm(answerForm);
            // The room's owner grants admin privileges to userName
            muc.grantAdmin(getJID(userName));

            //权限
            //muc.grantAdmin(connection.getUser());

        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (SmackException e) {
            e.printStackTrace();
        }
    }



    public static String getRoomJID(String user){
        return  new String(user+"@conference.pwgzl8wcrkhmt45");
    }

    public static String getJID(String user){
        return  new String(user+"@pwgzl8wcrkhmt45/Spark");
    }


    public static AbstractXMPPConnection connectToService() {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setServiceName(RestSetting.SERVICE_NAME)
                .setHost(RestSetting.SERVICE_HOST)
                .setPort(RestSetting.SERVICE_PORT)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(RestSetting.SERVICE_ISDEBUG)
                .setUsernameAndPassword(RestSetting.AUTH_USERNAME,RestSetting.AUTH_PASSWORD)
                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);
        try {

            connection.connect();

        } catch (SmackException e) {
            LogUtils.WriteError(null, e);
        } catch (IOException e) {
            LogUtils.WriteError(null, e);
        } catch (XMPPException e) {
            LogUtils.WriteError(null, e);
        }

        return connection;
    }
}
