package com.sharegroup.rest.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.sharegroup.rest.bean.RequestCompMessage;
import com.sharegroup.rest.bean.RequestCompMessageList;
import com.sharegroup.rest.model.chatMessage;
import com.sharegroup.rest.openfire.SmackManager;
import com.sharegroup.rest.utils.LogUtils;
import com.sharegroup.rest.utils.ReturnResult;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.cubilose.common.log.ILog;
import org.cubilose.common.log.LogAdapter;
import org.jivesoftware.smack.SmackException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
/*@Singleton
@Path("/chatservice")*/
@Controller
@RequestMapping("/compservice")
@Api(value = "compservice", description = "订阅号服务API", position = 2)
public class CompService {

    static ILog logger = new LogAdapter(CompService.class);
    private  static String MESSAGE_STYLE = "comp";

    Gson g = new Gson();

    /**
     * 用户发送消息接口
     * @param toUser 接收方用户信息
     * @param comp 接收方用户需要接收的消息内容
     */
    @ApiOperation(value = "发送消息", notes = "以管理员身份向指定用户<a>toUser</a>发送消息", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/{toUser}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult sentChatMessage(
            @ApiParam(name = "toUser",value = "要接收信息的用户")
            @PathVariable(value = "toUser") String toUser ,
            @ApiParam(name="comp",value="消息体内容")
            @RequestBody RequestCompMessage comp
    ) {
        logger.info("sentChatMessage",toUser,comp);
        chatMessage msg = new chatMessage();
        if(toUser==null){
            return ReturnResult.FAILUER("用户为空");
        }else if(comp == null){
            return ReturnResult.FAILUER("消息为空");
        }

        try {

            msg.setToUser(toUser);
            msg.setMessage(g.toJson(comp));
            SmackManager.getChatManager().sentMessage(msg,MESSAGE_STYLE);

        } catch (Exception ex) {
            LogUtils.WriteError("/compmessage/{toUser}",ex);
            return ReturnResult.FAILUER(ex.getMessage());
        }

        return ReturnResult.SUCCESS("comp SENT SUCCESS");
    }


    @ApiOperation(value = "消息群发", notes = "指定一条消息内容发送给多个用户", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/toMore", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult cmdMessageToMore(
            @ApiParam(value = "要推送的用户及用户消息")
            @RequestBody RequestCompMessageList comp) {
        logger.info("cmdMessageToMore",comp);
        List<String> userList = comp.getUserList();
        String sentmessage = g.toJson(comp.getCompMessage());
        LogUtils.WriteInfo("comp toMORE:" + sentmessage);
        for (String str : userList) {

            chatMessage msg = new chatMessage();
            msg.setMessage(sentmessage);

            msg.setToUser(str);

            try {
                SmackManager.getChatManager().sentMessage(msg, MESSAGE_STYLE);
            } catch (SmackException.NotConnectedException e) {
                LogUtils.WriteError("comp toMORE:", e);
                return ReturnResult.FAILUER(e.getMessage());
            }
        }

        return ReturnResult.SUCCESS("comp SENT SUCCESS");

    }
}
