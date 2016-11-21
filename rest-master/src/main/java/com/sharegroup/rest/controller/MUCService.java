package com.sharegroup.rest.controller;

import com.google.gson.Gson;
import com.sharegroup.rest.bean.*;
import com.sharegroup.rest.model.chatMessage;
import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.openfire.SmackManager;
import com.sharegroup.rest.service.MUCManager;
import com.sharegroup.rest.utils.Affiliation;
import com.sharegroup.rest.utils.LogUtils;
import com.sharegroup.rest.utils.MUC_JID;
import com.sharegroup.rest.utils.ReturnResult;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.jivesoftware.openfire.plugin.rest.entity.MUCRoomEntity;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.Occupant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/12.
 * 讲坛操作接口
 */
@Controller
@RequestMapping("/pulpitservice")
@Api(value = "pulpitservice", description = "讲坛相关API", position = 3)
public class MUCService {

    private static String MESSAGE_STYLE = "groupCmd";

    private static Gson g = new Gson();


    /**
     * 创建讲坛
     */
    @ApiOperation(value = "创建讲坛", notes = "管理员创建一个讲坛", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/createRoom/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult creatRoom(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(value = "创建讲坛信息")
            @RequestBody RequestMUCInfo muc
    ) {
        LogUtils.WriteInfo("pulpit create room :" + g.toJson(muc));
        MUCManager mucManager = SmackManager.getMucManager(token);

        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(muc.getRoomId());
        if (oldRoom != null) {
            return ReturnResult.FAILUER(oldRoom.getRoomName() + " alread exists");
        }

        int status = mucManager.createPulpitRoom(muc);
        if (201 == status) {
            MUCRoomEntity room = mucManager.queryPulpitRoom(muc.getRoomId());
            //创建成功后，添加ibaby为群属主
            mucManager.addPulpitOwner(muc.getRoomId(), RestSetting.AUTH_USERNAME);
            return ReturnResult.SUCCESS(room);
        }
        return ReturnResult.SUCCESS("create error : " + status);
    }

    /**
     * 创建讲坛
     */
    @ApiOperation(value = "结束讲坛", notes = "管理员结束一个讲坛", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/removeRoom/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult removeRoom(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(value = "创建讲坛信息")
            @RequestBody RequestMUCRemove muc
    ) {
        LogUtils.WriteInfo("pulpit remove room :" + g.toJson(muc));
        MUCManager mucManager = SmackManager.getMucManager(token);

        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(muc.getRoomId());
        if (oldRoom == null) {
            return ReturnResult.FAILUER(muc.getRoomId() + " does not  exists");
        }
        /**
         * 验证该用户是否可以删除
         */
        List<String> owners = oldRoom.getOwners();
        boolean flag = false;
        String owner = MUC_JID.getJID(muc.getOwner());
        for (String str : owners) {
            if (owner.equals(str)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return ReturnResult.FAILUER(muc.getRoomId() + " does not remove auth");
        }
        //执行删除
        int status = mucManager.removePulpitRoom(muc.getRoomId());
        if (200 == status) {
            return ReturnResult.SUCCESS(muc.getRoomId() + " remove success");
        }
        return ReturnResult.SUCCESS("remove error : " + status);
    }

    /**
     * 添加成员信息，
     * 如果只添加一位，则采用member
     */
    @ApiOperation(value = "添加讲坛成员", notes = "添加讲坛成员(只添加一人)", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/addmember/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult addMember(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(name = "memberInfo", value = "成员信息：\n" +
                    "roomId为必填项，" +
                    "添加一位成员，采用member参数，传递成员信息(String) \n" +
                    "type 为枚举类型：OWNER,ADMIN,MEMBER,OUTCAST\"")
            @RequestBody RequestMemberInfo memberInfo
    ) {
        LogUtils.WriteInfo("pulpit add members:" + g.toJson(memberInfo));

        if (memberInfo == null && isEmpty(memberInfo.getRoomId())) {
            return ReturnResult.FAILUER("参数有误");
        }

        MUCManager mucManager = SmackManager.getMucManager(token);
        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(memberInfo.getRoomId());
        if (oldRoom != null) {

            if (!isEmpty(memberInfo.getMember())) {
                int status = 0;
                //添加属主
                if (Affiliation.owner == memberInfo.getType()) {
                    status = mucManager.addPulpitOwner(memberInfo.getRoomId(), memberInfo.getMember());
                }

                //添加管理员
                if (Affiliation.admin == memberInfo.getType()) {
                    status = mucManager.addPulpitAdmin(memberInfo.getRoomId(), memberInfo.getMember());
                }

                //添加成员
                if (Affiliation.member == memberInfo.getType()) {
                    status = mucManager.addPulpitMember(memberInfo.getRoomId(), memberInfo.getMember());
                }

                //踢出成员
                if (Affiliation.outcast == memberInfo.getType()) {
                    /**
                     * 踢人前看看此人是否在线，如果在线也要强制要求退出房间
                     */

                    status = mucManager.PulpitMemberOuccast(memberInfo.getRoomId(), memberInfo.getMember());
                }
                return ReturnResult.SUCCESS(status);
            }
        } else {
            return ReturnResult.FAILUER(memberInfo.getRoomId() + " does not exists");
        }
        return ReturnResult.SUCCESS();
    }

    /**
     * 添加成员信息，
     * 如果添加多位，则采用members（List<String>列表）
     */
    @ApiOperation(value = "添加讲坛成员", notes = "添加讲坛成员(一次添加多人)", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/addmemberlist/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult addMemberList(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(name = "memberInfo", value = "成员信息：\n" +
                    "roomId为必填项，\n" +
                    "用members参数，传递成员列表（List<String>）\n" +
                    "type 为枚举类型：ADMIN,MEMBER,OUTCAST")
            @RequestBody RequestMemberInfoList memberInfo
    ) {
        LogUtils.WriteInfo("pulpit add members:" + g.toJson(memberInfo));

        if (memberInfo == null && isEmpty(memberInfo.getRoomId())) {
            return ReturnResult.FAILUER("参数有误");
        }

        MUCManager mucManager = SmackManager.getMucManager(token);
        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(memberInfo.getRoomId());
        if (oldRoom != null) {
            if (memberInfo.getMembers() != null && memberInfo.getMembers().size() > 0) {

                //添加管理员
                if (Affiliation.admin == memberInfo.getType()) {
                    mucManager.addPulpitAdmin(memberInfo.getRoomId(), memberInfo.getMembers());
                }

                //添加成员
                if (Affiliation.member == memberInfo.getType()) {
                    mucManager.addPulpitMember(memberInfo.getRoomId(), memberInfo.getMembers());
                }

                //踢出成员
                if (Affiliation.outcast == memberInfo.getType()) {
                    mucManager.PulpitMemberOuccast(memberInfo.getRoomId(), memberInfo.getMembers());
                }
                return ReturnResult.SUCCESS();
            } else {
                return ReturnResult.FAILUER("无可添加成员信息");
            }

        } else {
            return ReturnResult.FAILUER(memberInfo.getRoomId() + " does not exists");
        }
    }

    /**
     * 群发消息，发送透传消息
     */
    @ApiOperation(value = "群透传命令", notes = "透传消息，群发", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/groupcommand/toAll/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult groupCommandALL(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(name = "command", value = "透传命令：\n" +
                    "以管理员身份发送消息")
            @RequestBody RequestMUCCommand command
    ) {
        LogUtils.WriteInfo("pulpit groupcommand/toALL:" + g.toJson(command));

        if (command == null && isEmpty(command.getRoomId()))
            return ReturnResult.FAILUER("参数有误");

        MUCManager mucManager = SmackManager.getMucManager(token);
        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(command.getRoomId());
        if (oldRoom != null) {
            String msg = SmackManager.getChatManager().sentMUCMessage(command, MESSAGE_STYLE, Message.Type.groupchat);
            return ReturnResult.SUCCESS(msg);
        } else {
            return ReturnResult.FAILUER(command.getRoomId() + " does not exists");
        }
    }

    /**
     * 发私聊消息，发送透传消息
     */
    @ApiOperation(value = "群透传命令", notes = "透传消息，私信", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/groupcommand/toONE/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult groupCommandONE(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(name = "command", value = "透传命令：\n" +
                    "管理员登录发送消息" +
                    "只给 userId 用户发送消息")
            @RequestBody RequestMUCCommand command
    ) {
        LogUtils.WriteInfo("pulpit groupcommand/toONE:" + g.toJson(command));

        if (command == null && isEmpty(command.getRoomId()))
            return ReturnResult.FAILUER("参数有误");

        MUCManager mucManager = SmackManager.getMucManager(token);
        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(command.getRoomId());
        if (oldRoom != null) {
            String msg = SmackManager.getChatManager().sentMUCMessage(command, MESSAGE_STYLE, Message.Type.chat);
            return ReturnResult.SUCCESS(msg);
        } else {
            return ReturnResult.FAILUER(command.getRoomId() + " does not exists");
        }
    }

    /**
     * 一对一方式发送系统消息
     */
    /*@ApiOperation(value = "群系统消息", notes = "群系统消息", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/groupcommand/sys/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult groupCommandSYS(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(name = "command", value = "透传命令：\n" +
                    "管理员登录发送消息" +
                    "只给 userId 用户发送消息")
            @RequestBody RequestMUCCommand command
    ) {
        LogUtils.WriteInfo("pulpit groupcommand/sys:" + g.toJson(command));

        if (command == null && isEmpty(command.getRoomId()))
            return ReturnResult.FAILUER("参数有误");

        MUCManager mucManager = SmackManager.getMucManager(token);
        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(command.getRoomId());
        if (oldRoom != null) {

            RequestCommandMessage commandMessage = new RequestCommandMessage();
            //讲坛ID
            commandMessage.setCacheValue(command.getRoomId());
            //消息类型
            commandMessage.setCacheType(command.getCommondType());
            //提示内容
            commandMessage.setCacheTitle((String) command.getValue());

            chatMessage msg = new chatMessage();

            msg.setMessage(g.toJson(commandMessage));
            msg.setToUser(command.getUserId());

            try {
                SmackManager.getChatManager().sentMessage(msg, "command");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            return ReturnResult.SUCCESS(msg);
        } else {
            return ReturnResult.FAILUER(command.getRoomId() + " does not exists");
        }
    }*/

    @ApiOperation(value = "群系统消息", notes = "群系统消息", response = ReturnResult.class, position = 1)
    @RequestMapping(value = "/groupcommand/sys/{token}", method = RequestMethod.POST)
    public
    @ResponseBody
    ReturnResult groupCommandSYS(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(name = "command", value = "透传命令：\n" +
                    "管理员登录发送消息" +
                    "只给 userId 用户发送消息")
            @RequestBody RequestMUCCommandList commandList
    ) {
        RequestMUCCommand command = commandList.getCommand();
        LogUtils.WriteInfo("pulpit groupcommand/sys:" + g.toJson(command));

        if (command == null && isEmpty(command.getRoomId()))
            return ReturnResult.FAILUER("参数有误");

        MUCManager mucManager = SmackManager.getMucManager(token);
        MUCRoomEntity oldRoom = mucManager.queryPulpitRoom(command.getRoomId());
        if (oldRoom != null) {
            if(commandList.getUserList() != null && commandList.getUserList().size() >0) {
                for (String userId : commandList.getUserList()) {
                    sentMessage(command, userId);
                }
            }else{
                //默认一个用户
                sentMessage(command,null);
            }
            return ReturnResult.SUCCESS();
        } else {
            return ReturnResult.FAILUER(command.getRoomId() + " does not exists");
        }
    }

    private void sentMessage(RequestMUCCommand command, String userId) {
        RequestCommandMessage commandMessage = new RequestCommandMessage();
        //讲坛ID
        commandMessage.setCacheValue(command.getRoomId());
        //消息类型
        commandMessage.setCacheType(command.getCommondType());
        //提示内容
        commandMessage.setCacheTitle((String) command.getValue());

        chatMessage msg = new chatMessage();

        msg.setMessage(g.toJson(commandMessage));

        if(userId !=null) {
            msg.setToUser(userId);
        }
        try {
            SmackManager.getChatManager().sentMessage(msg, "command");
        } catch (SmackException.NotConnectedException e) {
            LogUtils.WriteError("pulpit groupcommand/sys:", e);
        }
    }

    /**
     * 2016-04-27 统计讲坛用户
     * @param pulpitId
     * @return
     */
    /*@ApiOperation(value = "usercount", notes = "统计在讲坛中的在线用户(实时用户)", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/usercount/{pulpitId}/{token}", method = RequestMethod.GET)
    @ResponseBody
    public
    ReturnResult userCountByRoom(
            @ApiParam(value = "讲坛编号")
            @PathVariable String pulpitId) {
        LogUtils.WriteInfo("统计在讲坛中的在线用户 :",pulpitId);
        List<Occupant> list  = SmackManager.getChatManager().getMUCUser(pulpitId);
        return  ReturnResult.SUCCESS(list.size());
    }*/

    /*@ApiOperation(value = "userList", notes = "讲坛中的在线用户列表(实时用户)", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/userList/{pulpitId}", method = RequestMethod.GET)
    @ResponseBody
    public
    ReturnResult userListByRoom(
            @ApiParam(value = "讲坛编号")
            @PathVariable String pulpitId) {
        LogUtils.WriteInfo("统计在讲坛中的在线用户 :",pulpitId);
        List<Occupant> list = SmackManager.getChatManager().getMUCUser(pulpitId);
        Map<String,String> onlineList = new HashMap<String,String>();
        if(list != null)
        for(Occupant occ : list){
            int index = occ.getJid().indexOf("@");
            String member = occ.getJid().substring(0,index);
            onlineList.put(member,occ.getNick());
        }
        return  ReturnResult.SUCCESS(onlineList);
    }*/

    @ApiOperation(value = "userList", notes = "讲坛中的在线用户列表(实时用户)", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/userList/{pulpitId}/{token}", method = RequestMethod.GET)
    @ResponseBody
    public
    ReturnResult userListByRoom2(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(value = "讲坛编号")
            @PathVariable String pulpitId) {
        LogUtils.WriteInfo("统计在讲坛中的在线用户 :",pulpitId);
        List<String> list = SmackManager.getMucManager(token).getChatRoomParticipants(pulpitId);
        return  ReturnResult.SUCCESS(list);
    }

    @ApiOperation(value = "usercount", notes = "统计在讲坛中的在线用户(实时用户)", response = ReturnResult.class, position = 3)
    @RequestMapping(value = "/usercount/{pulpitId}/{token}", method = RequestMethod.GET)
    @ResponseBody
    public
    ReturnResult userCountByRoom2(
            @ApiParam(name = "token", value = "安全验证参数")
            @PathVariable(value = "token") String token,
            @ApiParam(value = "讲坛编号")
            @PathVariable String pulpitId) {
        LogUtils.WriteInfo("统计在讲坛中的在线用户 :",pulpitId);
        List<String> list = SmackManager.getMucManager(token).getChatRoomParticipants(pulpitId);
        return  ReturnResult.SUCCESS(list.size());
    }

    private Boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

}
