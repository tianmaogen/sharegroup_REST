package com.sharegroup.rest.bean;

/**
 * 透传消息：
 * Created by Administrator on 2015/12/7.
 */
public class RequestCommandMessage {

    /**
     * 消息类型
     * 系统消息：102
     * 评论消息:103
     * 免费问诊消息：104
     * 关闭问诊:105
     */
    private Integer CacheType;

    /**
     * 消息附加信息
     * 免费问诊消息会话ID,关闭问诊会话ID等
     */
    private String CacheValue;

    /**
     * 提示消息内容
     */
    private String CacheTitle;

    public Integer getCacheType() {
        return CacheType;
    }

    public void setCacheType(Integer cacheType) {
        CacheType = cacheType;
    }

    public String getCacheValue() {
        return CacheValue;
    }

    public void setCacheValue(String cacheValue) {
        CacheValue = cacheValue;
    }

    public String getCacheTitle() {
        return CacheTitle;
    }

    public void setCacheTitle(String cacheTitle) {
        CacheTitle = cacheTitle;
    }
}
