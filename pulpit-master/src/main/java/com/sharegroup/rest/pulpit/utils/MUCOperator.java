package com.sharegroup.rest.pulpit.utils;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MUCOperator {

    /*
     * 讲坛相关
     */
    public static String CREATE_PULPIT_ROOM = "createRoom";

    public static String REMOVE_PULPIT_ROOM = "removeRoom";
    /**
     * 透传消息相关
     */
    //命令单发
    public static String SENDIN_STRUCTION_TO_ONE = "groupcommand/toONE";
    //命令群发
    public static String SENDIN_STRUCTION_TO_ALL = "groupcommand/toAll";
    //一对一命令发送
    public static String SENDIN_STRUCTION_TO_SYS = "groupcommand/sys";

    /**
     * 成员相关
     * 注意 MUCMemberInfo 类型必需指定 Affiliation.owner ,admin 等
     */
    public static String PULPIT_MEMBER = "addmember";

    public static String PULPIT_MEMBER_LIST = "addmemberlist";


}
