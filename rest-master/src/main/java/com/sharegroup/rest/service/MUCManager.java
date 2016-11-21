package com.sharegroup.rest.service;

import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.bean.RequestMUCInfo;
import com.sharegroup.rest.openfire.RestSetting;
import com.sharegroup.rest.packet.MucApiClient;
import com.sharegroup.rest.utils.MUC_JID;
import org.jivesoftware.openfire.plugin.rest.entity.MUCRoomEntity;
import org.jivesoftware.openfire.plugin.rest.entity.ParticipantEntities;
import org.jivesoftware.openfire.plugin.rest.entity.ParticipantEntity;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class MUCManager {

    //服务器认证
    private MucApiClient mucApiClient;

    public static int DEFAULT_MAX_USER = 999;


    public MUCManager(String token){
        AuthenticationToken auth = new AuthenticationToken(token);

        mucApiClient = new MucApiClient(RestSetting.SERVICE_ADMIN, RestSetting.SERVICE_ADMIN_PORT, auth);
    }

    /**
     * 查询讲坛信息
     * @param roomId
     * @return
     */
    public MUCRoomEntity  queryPulpitRoom(String roomId){
        return mucApiClient.getChatRoom(roomId);
    }

    /**
     * 利用room admin创建讲坛房间
     * @param mucInfo 创建房间参数
     * @return
     */
    public int createPulpitRoom(RequestMUCInfo mucInfo){


        MUCRoomEntity entity = new MUCRoomEntity(mucInfo.getRoomId(), MUC_JID.getRoomJID(mucInfo.getRoomName()),mucInfo.getDesc());

        //设置群人数
        int maxuser  = mucInfo.getMaxUsers() == null ? DEFAULT_MAX_USER : mucInfo.getMaxUsers() ;
        entity.setMaxUsers(maxuser);
        //设置为公共房间
        entity.setPublicRoom(true);
        //设置为永久房间
        entity.setPersistent(true);
        //允许修改昵称
        entity.setCanChangeNickname(true);
        //允许用户登录注册房间
        entity.setRegistrationEnabled(true);
        //设置主题
        entity.setSubject(mucInfo.getSubject());
        //仅对成员公开
        //entity.setMembersOnly(true);

        /*
        List<String> cast_values = new ArrayList<String>();
        cast_values.add("moderator");
        cast_values.add("participant");
        cast_values.add("visitor");
        entity.setBroadcastPresenceRoles(cast_values);
        */
        entity.setCanAnyoneDiscoverJID(true);

        mucApiClient.createChatRoom(entity);

        Response response = mucApiClient.createChatRoom(entity);
        return response.getStatus();
    }

    /**
     * 结束讲坛，删除群
     * @param roomId
     * @return
     */
    public int removePulpitRoom(String roomId){
        Response response =mucApiClient.deleteChatRoom(roomId);
        return response.getStatus();
    }

    /**
     * 添加群属主 ，一般只用创建者
     * @param roomId 创建房间的管理员名称
     *              eg. ibaby
     * @param ownerNick 要添加的成员名称
     *              eg. ibaby
     */
    public int addPulpitOwner(String roomId,String ownerNick){

        Response response = mucApiClient.addOwner(roomId, ownerNick);
        return response.getStatus();
    }

    /**
     * 添加讲坛管理员
     * @param roomId 创建房间的管理员名称
     *              eg. ibaby
     * @param nickList 要添加的成员名称列表
     *              eg. [jade,wendy]
     * @return
     */
    public void addPulpitAdmin(String roomId,List<String> nickList){
        for(String nick : nickList){
            addPulpitAdmin(roomId,nick);
        }
    }

    /**
     * 添加讲坛管理员
     * @param roomId 创建房间的管理员名称
     *              eg. ibaby
     * @param adminNick 要添加的成员名称
     *              eg. jade
     * @return
     */
    public int addPulpitAdmin(String roomId,String adminNick){
        Response response = mucApiClient.addAdmin(roomId, adminNick);
        return response.getStatus();
    }

    /**
     * 添加讲坛成员
     * @param roomId 创建房间的管理员名称
     *              eg. ibaby
     * @param nickList 要添加的成员名称列表
     *              eg. [jade,wendy]
     */
    public void addPulpitMember(String roomId,List<String> nickList){
        for(String nick : nickList){
            if(!"".equals(nick))
            addPulpitMember(roomId, nick);
        }
    }

    /**
     * 添加讲坛成员
     * @param roomId 创建房间的管理员名称
     *              eg. ibaby
     * @param memberNick 要添加的成员名称
     *              eg. jade
     * @return
     */
    public int addPulpitMember(String roomId,String memberNick){
        Response response = mucApiClient.addMember(roomId, memberNick);
        return response.getStatus();
    }


    /**
     * 踢出群，不再进入
     * @param roomId 创建房间的管理员名称
     *              eg. ibaby
     * @param nickList 要踢出的成员名称列表
     *               eg. [jade,wendy]
     */
    public void PulpitMemberOuccast(String roomId,List<String> nickList){
        for(String nick : nickList){
            if(!"".equals(nick))
            PulpitMemberOuccast(roomId, nick);
        }
    }

    /**
     * 踢出群，不再进入
     * @param roomId 创建房间的管理员名称
     *              eg. ibaby
     * @param memberNick 要踢出的成员名称
     *              eg. jade
     * @return
     */
    public int PulpitMemberOuccast(String roomId,String memberNick){
        Response response = mucApiClient.addOutcast(roomId, memberNick);
        return response.getStatus();
    }

    public List<String> getChatRoomParticipants(String roomId){
        List<String> users = new ArrayList<String>();

        ParticipantEntities entities =  mucApiClient.getChatRoomParticipants(roomId);
        if(entities == null)
            return users;

        List<ParticipantEntity> entity = entities.getParticipants();
        if(entity == null)
            return users;

        for(ParticipantEntity ent: entity){
            String jid = ent.getJid();
            users.add(jid.substring(jid.indexOf("/")+1));
        }
        return users;
    }

    public MucApiClient getMucApiClient() {
        return mucApiClient;
    }

    public void setMucApiClient(MucApiClient mucApiClient) {
        this.mucApiClient = mucApiClient;
    }
}
