package com.sharegroup.rest.controller;

import com.google.gson.Gson;
import com.sharegroup.rest.bean.RequestChatMessage;
import com.sharegroup.rest.model.chatMessage;
import com.sharegroup.rest.openfire.SmackManager;
import com.sharegroup.rest.utils.LogUtils;
import com.sharegroup.rest.utils.ReturnResult;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.cubilose.common.log.ILog;
import org.cubilose.common.log.LogAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2015/12/3.
 */
/*@Singleton
@Path("/chatservice")*/
@Controller
@RequestMapping("/chatservice")
@Api(value = "chatservice", description = "聊天服务API", position = 2)
public class ChatService {

    static ILog logger = new LogAdapter(ChatService.class);

    private static String MESSAGE_STYLE = "message";


    /**
     * 用户发送消息接口
     *
     * @param toUser 接收方用户信息
     * @param rcmsg  接收方用户需要接收的消息内容
     */
    @ApiOperation(value = "发送消息", notes = "以管理员身份向指定用户<a>toUser</a>发送消息", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/chatmessage/{toUser}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult sentChatMessage(
            @ApiParam(name = "toUser", value = "要接收信息的用户")
            @PathVariable(value = "toUser") String toUser,
            @ApiParam(name = "rcmsg", value = "消息体内容")
            @RequestBody RequestChatMessage rcmsg
    ) {
        logger.info("chatmessage", toUser, rcmsg);
        chatMessage msg = new chatMessage();
        if (toUser == null) {
            return ReturnResult.FAILUER("用户为空");
        } else if (rcmsg == null) {
            return ReturnResult.FAILUER("消息为空");
        }

        try {
            Gson g = new Gson();
            msg.setToUser(toUser);
            msg.setMessage(g.toJson(rcmsg));
            SmackManager.getChatManager().sentMessage(msg, MESSAGE_STYLE);

        } catch (Exception ex) {
            LogUtils.WriteError("/chatmessage/{toUser}", ex);
            return ReturnResult.FAILUER(ex.getMessage());
        }

        return ReturnResult.SUCCESS("SENT SUCCESS");
    }
}
