package org.jivesoftware.openfire.plugin.ibaby.module;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 上午9:53
 * To change this template use File | Settings | File Templates.
 * 同步pulpit 接口消息
 */
public class PulpitMessage {

    /// <summary>
    /// 聊天类型 1-免费问诊 2-图文问诊 3-医患聊天  4-好友聊天
    /// </summary>
    private Integer ChatType;
    /// <summary>
    /// 会话编号   指定讲坛
    /// </summary>
    private String ChatMainId;
    /// <summary>
    /// 客户端消息编号
    /// </summary>
    private String ClientMsgId;
    /// <summary>
    /// 聊天对象类型
    /// </summary>
    private Integer ObjectType;
    /// <summary>
    /// 聊天对象编号
    /// </summary>
    private String ObjectId;
    /// <summary>
    /// 聊天对象名称
    /// <summary>
    private String ObjectName;
    /// <summary>
    /// 聊天对象头像
    /// </summary>
    private String ObjectHead;
    /// <summary>
    /// 聊天内容类型(10:文字,11:图片,12:坐标,13:声音,14:BBS)
    /// </summary>
    private Integer ContentType;
    /// <summary>
    /// 聊天内容
    /// </summary>
    private String Content;
    /// <summary>
    /// 附件类型
    /// </summary>
    private String ContentUrl;
    /// <summary>
    /// 扩展内容(图片，坐标，声音的Json字段)
    /// </summary>
    private String ExpandJson;
    /// <summary>
    /// 聊天时间
    /// </summary>
    private Date Created;

    public Integer getChatType() {
        return ChatType;
    }

    public void setChatType(Integer chatType) {
        ChatType = chatType;
    }

    public String getChatMainId() {
        return ChatMainId;
    }

    public void setChatMainId(String chatMainId) {
        ChatMainId = chatMainId;
    }

    public String getClientMsgId() {
        return ClientMsgId;
    }

    public void setClientMsgId(String clientMsgId) {
        ClientMsgId = clientMsgId;
    }

    public Integer getObjectType() {
        return ObjectType;
    }

    public void setObjectType(Integer objectType) {
        ObjectType = objectType;
    }

    public String getObjectId() {
        return ObjectId;
    }

    public void setObjectId(String objectId) {
        ObjectId = objectId;
    }

    public String getObjectName() {
        return ObjectName;
    }

    public void setObjectName(String objectName) {
        ObjectName = objectName;
    }

    public String getObjectHead() {
        return ObjectHead;
    }

    public void setObjectHead(String objectHead) {
        ObjectHead = objectHead;
    }

    public Integer getContentType() {
        return ContentType;
    }

    public void setContentType(Integer contentType) {
        ContentType = contentType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContentUrl() {
        return ContentUrl;
    }

    public void setContentUrl(String contentUrl) {
        ContentUrl = contentUrl;
    }

    public String getExpandJson() {
        return ExpandJson;
    }

    public void setExpandJson(String expandJson) {
        ExpandJson = expandJson;
    }

    public Date getCreated() {
        return Created;
    }

    public void setCreated(Date created) {
        Created = created;
    }
}
