package com.sharegroup.test;

import com.sharegroup.rest.bean.AuthenticationToken;
import com.sharegroup.rest.packet.MucApiClient;
import org.jivesoftware.openfire.plugin.rest.entity.MUCRoomEntity;
import org.jivesoftware.openfire.plugin.rest.entity.ParticipantEntities;
import org.jivesoftware.openfire.plugin.rest.entity.ParticipantEntity;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RestApiMucTest {


    static int classID = 02;
    static String roomId = "ibaby"+classID;
    static String name = "pulpit_room_"+classID;
    static String desc = "this is ibaby pulpit";
    static String subject = "ibaby subject";
    static String nick = "admin"+classID;


    public static void main(String [] args){
        //XRbghp7zHw8Yx6gX   2RRO7i13GqNryj3a
        AuthenticationToken authenticationToken = new AuthenticationToken("2RRO7i13GqNryj3a");
        //192.168.1.222  127.0.0.1
        MucApiClient mucApiClient = new MucApiClient("192.168.1.58", 9090, authenticationToken);

        //创建测试
        createRoomTest(mucApiClient);
        //手动添加成员信息及修改成员权限
        //roomAddAdmin(mucApiClient);
        //roomAddMember(mucApiClient);
        //roomAddOwner(mucApiClient);
        //roomAddOutcast(mucApiClient);
        //roomRemove(mucApiClient);

        //初始化添加，一次添加完成，会修改主体信息
        //roomAddAdmins(mucApiClient);
        //roomAddOwners(mucApiClient);
        // roomAddMembers(mucApiClient);

        /*ParticipantEntities participantEntities = mucApiClient.getChatRoomParticipants(roomId);
        List<ParticipantEntity> list = participantEntities.getParticipants();
        for(ParticipantEntity p : list){
            System.out.println("jid : "+p.getJid()+" affiliation:"+ p.getAffiliation() +"  role:"+ p.getRole());
        }

        MUCRoomEntity muc = mucApiClient.getChatRoom(roomId);
        System.out.println(muc.getRoomName());
        List<String> members = muc.getMembers();
        System.out.println("members count = "+members.size());*/
    }

    /**
     * 添加讲坛管理员
     * @param mucApiClient
     */
    private static void roomAddAdmin(MucApiClient mucApiClient){

        Response response = mucApiClient.addAdmin(roomId, "jade");
        System.out.println("admins init : " + response.getStatus());

//        for(int i=0;i<10;i++) {
//            Response response = mucApiClient.addAdmin(roomId, "admin"+i);
//            System.out.println("admins init : " + response.getStatus());
//        }
    }


 private static void roomAddAdmins(MucApiClient mucApiClient){

        MUCRoomEntity entity = getMucRoomEntity();
        List<String> admins = new ArrayList<String>();
        admins.add(getJID("ibaby"));
        admins.add(getJID("lucy"));
        entity.setAdmins(admins);

        Response response = mucApiClient.updateChatRoom(entity);
        System.out.println("admins init : "+response.getStatus());
    }

    /**
     * 添加群属主 ，一般只有一个
     * @param mucApiClient
     */
    private static void roomAddOwner(MucApiClient mucApiClient){

        Response response = mucApiClient.addOwner(roomId, "ibaby");
        System.out.println("owner init : " + response.getStatus());

//        for(int i=0;i<10;i++) {
//            Response response = mucApiClient.addOwner(roomId, "ibaby"+i);
//            System.out.println("owner init : " + response.getStatus());
//        }
    }

    private static void roomAddOwners(MucApiClient mucApiClient){

        MUCRoomEntity entity = getMucRoomEntity();
        List<String> owners = new ArrayList<String>();
        owners.add(getJID(roomId));
        entity.setOwners(owners);

        Response response = mucApiClient.updateChatRoom(entity);
        System.out.println("owners init : "+response.getStatus());
    }

    /**
     * 添加讲坛成员
     *    管理员拉人进入
     * @param mucApiClient
     */
    private static void roomAddMember(MucApiClient mucApiClient){

        Response response = mucApiClient.addMember(roomId, "jade");
        System.out.println("member init : " + response.getStatus());

//        for(int i=0;i<101;i++) {
//            Response response = mucApiClient.addMember(roomId, "jade"+i);
//            System.out.println("admins init : " + response.getStatus());
//        }
    }

    private static void roomAddMembers(MucApiClient mucApiClient){

        MUCRoomEntity entity = getMucRoomEntity();
        List<String> members = new ArrayList<String>();
        for(int i=0;i<100;i++) {
            members.add(getJID("jade"+i));
            entity.setMembers(members);

        }
        Response response =  mucApiClient.updateChatRoom(entity);
        System.out.println("members init : "+response.getStatus());
    }


    /**
     * 踢出讲坛
     * @param mucApiClient
     */
    private static void roomAddOutcast(MucApiClient mucApiClient) {

        Response response = mucApiClient.addOutcast(roomId, "jadeadmin");
        System.out.println("member init : " + response.getStatus());

    }

    /**
     * 结束讲坛
     * @param mucApiClient
     */
    private static void roomRemove(MucApiClient mucApiClient) {

        Response response = mucApiClient.deleteChatRoom(roomId);
        System.out.println("member init : " + response.getStatus());

    }



    private static MUCRoomEntity getMucRoomEntity() {

        MUCRoomEntity entity = new MUCRoomEntity(roomId,getRoomJID(name),desc);

        //设置群人数
        entity.setMaxUsers(100);
        //设置为公共房间
        entity.setPublicRoom(true);
        //设置为永久房间
        entity.setPersistent(true);
        //允许修改昵称
        entity.setCanChangeNickname(true);
        //允许用户登录注册房间
        entity.setRegistrationEnabled(true);
        //设置主题
        entity.setSubject(subject);
        //仅对成员公开
        //entity.setMembersOnly(true);

        List<String> cast_values = new ArrayList<String>();
        cast_values.add("moderator");
        cast_values.add("participant");
        cast_values.add("visitor");
        entity.setBroadcastPresenceRoles(cast_values);

        entity.setCanAnyoneDiscoverJID(true);
        return entity;
    }

    private static void createRoomTest(MucApiClient mucApiClient ) {

        MUCRoomEntity entity = getMucRoomEntity();
//
//        List<String> broadcastPresenceRoles = new ArrayList<String>();
//        broadcastPresenceRoles.add("admins");
//        entity.setBroadcastPresenceRoles(broadcastPresenceRoles);

        Response response = mucApiClient.createChatRoom(entity);
        System.out.println(response.getStatus());
    }


    public static String getRoomJID(String user){
        return  new String(user+"@conference.pwgzl8wcrkhmt45");
    }

    public static String getJID(String user){
        return  new String(user+"@pwgzl8wcrkhmt45/Spark");
    }




}