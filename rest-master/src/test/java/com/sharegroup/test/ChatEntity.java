package com.sharegroup.test;


import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ChatEntity {

    private String chatType;
    private String chatMainId;
    private String clientMsgId;
    private String objectType;
    private String objectId;
    private String objectName;
    private String objectHead;
    private String contentType;
    private String content;
    private String contentUrl;
    private String expandJson;
    private String created;
    private String notReadCount;
    private String noteName;



    public boolean isRepeatable() {
        return false;
    }

    public void writeRequest(OutputStream outputStream) throws IOException {

    }

    public long getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getChatMainId() {
        return chatMainId;
    }

    public void setChatMainId(String chatMainId) {
        this.chatMainId = chatMainId;
    }

    public String getClientMsgId() {
        return clientMsgId;
    }

    public void setClientMsgId(String clientMsgId) {
        this.clientMsgId = clientMsgId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectHead() {
        return objectHead;
    }

    public void setObjectHead(String objectHead) {
        this.objectHead = objectHead;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getExpandJson() {
        return expandJson;
    }

    public void setExpandJson(String expandJson) {
        this.expandJson = expandJson;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getNotReadCount() {
        return notReadCount;
    }

    public void setNotReadCount(String notReadCount) {
        this.notReadCount = notReadCount;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
}
