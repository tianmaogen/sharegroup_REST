package org.jivesoftware.openfire.plugin.ibaby.module;

/**
 * .net端请求发送消息
 * Created by Administrator on 2015/12/7.
 */
public class ClientChatMessage {

    private String ChatType;
    private String ChatMainId;
    private String ClientMsgId;
    private String ObjectType;
    private String ObjectId;
    private String ObjectName;
    private String ObjectHead;
    private String ContentType;
    private String Content;
    private String ContentUrl;
    private String ExpandJson;
    private String Created;
    private String NotReadCount;
    private String NoteName;
    private String ToId;


    public String getChatType() {
        return ChatType;
    }

    public void setChatType(String chatType) {
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

    public String getObjectType() {
        return ObjectType;
    }

    public void setObjectType(String objectType) {
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

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
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

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getNotReadCount() {
        return NotReadCount;
    }

    public void setNotReadCount(String notReadCount) {
        NotReadCount = notReadCount;
    }

    public String getNoteName() {
        return NoteName;
    }

    public void setNoteName(String noteName) {
        NoteName = noteName;
    }

    public String getToId() {
        return ToId;
    }

    public void setToId(String toId) {
        ToId = toId;
    }
}
