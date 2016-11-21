package com.sharegroup.rest.service;

import com.sharegroup.rest.bean.AccountChangePwd;
import com.sharegroup.rest.bean.AccountRegister;
import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.openfire.SmackManager;
import com.sharegroup.rest.packet.RestApiClient;
import org.jivesoftware.openfire.plugin.rest.entity.UserEntity;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/3.
 */
public class UserManager {

    /**
     * 登录测试方法
     * @param auth
     * @throws IOException
     * @throws XMPPException
     * @throws SmackException
     */
    public String userLogin(AuthenticationToken auth ) throws IOException, XMPPException, SmackException {


        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(auth.getUsername(), auth.getPassword())
                .setServiceName(RestSetting.SERVICE_NAME)
                .setHost(RestSetting.SERVICE_HOST)
                .setPort(RestSetting.SERVICE_PORT)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(RestSetting.SERVICE_ISDEBUG)


                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);

        connection.connect();
        connection.login();
        return connection.getUser();
    }


    /**
     * 注册用户服务
     *
     * @param account
     */


    public void createAccount(AccountRegister account) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {

        XMPPConnection connection = SmackManager.getSessionManager().getConnection();


        AccountManager manager = AccountManager.getInstance(connection);
        manager.sensitiveOperationOverInsecureConnection(true);
        manager.createAccount(account.getUserName(), account.getUserPwd());


    }

    /**
     * 修改用户密码
     *
     * @param account
     */
    public void ChangePassword(AccountChangePwd account) throws SmackException, XMPPException, IOException {

        AuthenticationToken token = new AuthenticationToken(account.getUsername(), account.getPassword());

        SmackManager.getUserManager().userLogin(token);
        XMPPConnection connection = SmackManager.getConnection();
        String user = connection.getUser();
        AccountManager manager = AccountManager.getInstance(connection);
        manager.sensitiveOperationOverInsecureConnection(true);
        manager.changePassword(account.getPasswordConfirm());


    }
    /**
     * 管理员重置用户密码
     *
     * @param token
     */
    public String ResetPassword(AuthenticationToken token,AccountRegister register){
        if( null == token){
            return "token不能为空";
        }
        //服务器认证
        RestApiClient restApiClient = new RestApiClient(RestSetting.SERVICE_ADMIN, RestSetting.SERVICE_ADMIN_PORT, token);

        UserEntity u = restApiClient.getUser(register.getUserName());
        if(u == null ){
            return "用户不存在";
        }
        u.setPassword(register.getUserPwd());
        Response r = restApiClient.updateUser(u);
        if(r.getStatus() == 200){
            return "重置密码成功";
        }else{
            return "重置密码失败";
        }

    }
}
