package com.sharegroup.rest.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.sharegroup.rest.bean.RequestCommandMessage;
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

/**
 * Created by Administrator on 2015/12/7.
 */
@Controller
@RequestMapping("/cmdservice")
@Api(value = "cmdservice", description = "透传消息服务API", position = 2)
public class CommandService {

    static ILog logger = new LogAdapter(CommandService.class);
    private static String MESSAGE_STYLE = "command";

    private static Gson g = new Gson();

    @ApiOperation(value = "消息单发", notes = "指定用户的一位发送消息", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/toOne/{user}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult cmdMessageToOne(
            @ApiParam(name = "user", value = "要接收消息的用户")
            @PathVariable(value = "user") String user,
            @ApiParam(value = "发送的消息体" +
                    "     * 消息类型\n" +
                    "     * 系统消息：102\n" +
                    "     * 评论消息:103\n" +
                    "     * 免费问诊消息：104\n" +
                    "     * 关闭问诊：105")
            @RequestBody RequestCommandMessage rcmsg
    ) {
        logger.info("cmdMessageToOne", user, rcmsg);
        String sentmessage;
        try {
//            //对rmessage做验证
            if (rcmsg.getCacheType() == null || rcmsg.getCacheType() == 0) {
                return ReturnResult.FAILUER("CacheType格式不对");
            }
            sentmessage = g.toJson(rcmsg);
            LogUtils.WriteInfo("cmd toONE:" + sentmessage);
        } catch (Exception ex) {
            LogUtils.WriteError("/toOne/{user}", ex);
            return ReturnResult.FAILUER(ex.getLocalizedMessage());
        }

        chatMessage msg = new chatMessage();
        msg.setMessage(sentmessage);
        msg.setToUser(user);

        try {
            SmackManager.getChatManager().sentMessage(msg, MESSAGE_STYLE);
        } catch (SmackException.NotConnectedException e) {
            return ReturnResult.FAILUER(e.getMessage());
        }
        return ReturnResult.SUCCESS();

//        return ReturnResult.SUCCESS("消息发送成功");
    }

    @ApiOperation(value = "消息群发", notes = "指定一条消息内容发送给多个用户", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/toMore", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult cmdMessageToMore(
            @ApiParam(value = "用户列表类型为List &lt;String&gt; \n eg: [\"lucy\",\"tom\"]")
            @RequestParam(value = "jsonUsers", required = true) String jsonUsers,
            @ApiParam(value = "发送的消息体" +
                    "     * 消息类型\n" +
                    "     * 系统消息：102\n" +
                    "     * 评论消息:103\n" +
                    "     * 免费问诊消息：104\n" +
                    "     * 关闭问诊:105\n")
            @RequestBody RequestCommandMessage rcmsg) {
        logger.info("cmdMessageToMore", jsonUsers, rcmsg);
        JSONArray userList = JSONArray.parseArray(jsonUsers);
        String sentmessage = g.toJson(rcmsg);
        LogUtils.WriteInfo("cmd toMORE:" + sentmessage);
        for (Iterator iterator = userList.iterator(); iterator.hasNext(); ) {

            chatMessage msg = new chatMessage();
            msg.setMessage(sentmessage);

            msg.setToUser(iterator.next().toString());

            try {
                SmackManager.getChatManager().sentMessage(msg, MESSAGE_STYLE);
            } catch (SmackException.NotConnectedException e) {
                LogUtils.WriteError("cmd toMORE:", e);
                return ReturnResult.FAILUER(e.getMessage());
            }
        }

        return ReturnResult.SUCCESS();

    }
}