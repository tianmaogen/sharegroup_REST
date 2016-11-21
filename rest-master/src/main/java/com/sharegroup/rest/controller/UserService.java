package com.sharegroup.rest.controller;


import com.sharegroup.rest.bean.AccountChangePwd;
import com.sharegroup.rest.bean.AccountRegister;
import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.openfire.SmackManager;
import com.sharegroup.rest.utils.LogUtils;
import com.sharegroup.rest.utils.ReturnResult;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.cubilose.common.log.ILog;
import org.cubilose.common.log.LogAdapter;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.Occupant;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-1
 * Time: 下午6:46
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/userservice")
@Api(value = "userservice", description = "用户服务API", position = 2)
public class UserService {

    static ILog logger = new LogAdapter(UserService.class);

    /**
     * 用户登录接口
     */
    //@ApiOperation(value = "login", notes = "用户登录,此接口为API测试专用，不提供.NET及其他应用调用,当出现not-authorized|Socket closed|Client is not, or no longer, connected错误时需要通过此方法(login)重新建立与openfire的连接", response = ReturnResult.class, position = 2)
    //@RequestMapping(value = "/login", method = RequestMethod.GET)
    public
    @ResponseBody
    ReturnResult LoginUsers() {
        logger.warn("login system ");
        AuthenticationToken auth = new AuthenticationToken(RestSetting.AUTH_USERNAME, RestSetting.AUTH_PASSWORD);
        return loginTest(auth);
    }


    @ApiOperation(value = "logintest", notes = "登录用户，以测试是用户密码是否正确", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/logintest", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult LoginUsersTest(
            @ApiParam(value = "登录用户")
            @RequestBody AccountRegister account

    ) {
        logger.info("登录用户 ",account);
        AuthenticationToken auth = new AuthenticationToken(account.getUserName(), account.getUserPwd());
        return loginTest(auth);
    }

    /**
     * 登录方法测试
     * @param auth
     * @return
     */
    private ReturnResult loginTest(AuthenticationToken auth) {
        String ss = null;
        try {
            ss = SmackManager.getUserManager().userLogin(auth);
        } catch (IOException e) {
             LogUtils.WriteError("", e);
            e.printStackTrace();
            return ReturnResult.FAILUER(e.getMessage());
        } catch (XMPPException e) {
             LogUtils.WriteError("", e);
            e.printStackTrace();
            return ReturnResult.FAILUER(e.getMessage());
        } catch (SmackException e) {
             LogUtils.WriteError("", e);
            e.printStackTrace();
            return ReturnResult.FAILUER(e.getMessage());
        }

        return ReturnResult.SUCCESS(ss);
    }


    //@ApiOperation(value = "register", notes = "用户注册", response = ReturnResult.class, position = 1)
    //@RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult registerAccount(
            @ApiParam(value = "注册用户信息,confirmPwd 为非必填项")
            @RequestBody AccountRegister account
    ) {
        if (account == null) {
            return ReturnResult.FAILUER("注册信息为空");
        } else if (account.getUserName() == null || "".equals(account.getUserName())) {
            return ReturnResult.FAILUER("用户名为空");
        } else if (account.getUserPwd() == null || "".equals(account.getUserPwd())) {
            return ReturnResult.FAILUER("密码为空");
        } else {
            try {
                SmackManager.getUserManager().createAccount(account);
            } catch (SmackException.NotConnectedException e) {
                 LogUtils.WriteError("", e);
                e.printStackTrace();
                return ReturnResult.FAILUER(e.getMessage());
            } catch (XMPPException.XMPPErrorException e) {
                 LogUtils.WriteError("", e);
                e.printStackTrace();
                return ReturnResult.FAILUER(e.getMessage());
            } catch (SmackException.NoResponseException e) {
                 LogUtils.WriteError("", e);
                e.printStackTrace();
                return ReturnResult.FAILUER(e.getMessage());
            }

//            String ss = SmackManager.getSessionManager().getJID();
            return ReturnResult.SUCCESS("注册成功");
        }


    }

    //@ApiOperation(value = "changepwd", notes = "修改密码", response = ReturnResult.class, position = 3)
    //@RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult changeAccountPassword(
            @ApiParam(value = "用户信息")
            @RequestBody AccountChangePwd account
    ) {
        if (account == null) {
            return ReturnResult.FAILUER("信息为空");
        } else if (account.getUsername() == null || "".equals(account.getUsername())) {
            return ReturnResult.FAILUER("用户名为空");
        } else if (account.getPassword() == null || "".equals(account.getPassword())) {
            return ReturnResult.FAILUER("密码为空");
        }  else if (account.getPasswordConfirm() == null || "".equals(account.getPasswordConfirm())) {
            return ReturnResult.FAILUER("新密码为空");
        } else {
            try {
                SmackManager.getUserManager().ChangePassword(account);
            } catch (SmackException.NotConnectedException e) {
                 LogUtils.WriteError("", e);
                e.printStackTrace();
                return ReturnResult.FAILUER(e.getMessage());
            } catch (XMPPException.XMPPErrorException e) {
                 LogUtils.WriteError("", e);
                e.printStackTrace();
                return ReturnResult.FAILUER(e.getMessage());
            } catch (SmackException.NoResponseException e) {
                 LogUtils.WriteError("", e);
                e.printStackTrace();
                return ReturnResult.FAILUER(e.getMessage());
            } catch (Exception e){
                 LogUtils.WriteError("", e);
                e.printStackTrace();
                return ReturnResult.FAILUER(e.getMessage());
            }
            String ss = SmackManager.getSessionManager().getUserNick();
            return ReturnResult.SUCCESS("密码修改成功");
        }


    }

    //@ApiOperation(value = "resetPwd", notes = "管理员登录重置用户密码,222测试机token=\"XRbghp7zHw8Yx6gX\"", response = ReturnResult.class, position = 2)
    //@RequestMapping(value = "/resetPwd/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult restUserPassword(
            @ApiParam(name = "token",value = "安全验证参数")
            @PathVariable(value = "token") String token ,
            @ApiParam(value = "用户信息")
            @RequestBody AccountRegister account) {

        if (token == null || token.length() != 16) {
            return ReturnResult.FAILUER("token验证不通过");
        } else if (account.getUserName() == null || "".equals(account.getUserName())) {
            return ReturnResult.FAILUER("用户名不能这空");
        } else if (account.getUserPwd() == null || "".equals(account.getUserPwd())) {
            return ReturnResult.FAILUER("密码为空");
        } else {

            AuthenticationToken auth = new AuthenticationToken(token);

            String  result = SmackManager.getUserManager().ResetPassword(auth,account);

            return ReturnResult.SUCCESS(result);

        }
    }

}
