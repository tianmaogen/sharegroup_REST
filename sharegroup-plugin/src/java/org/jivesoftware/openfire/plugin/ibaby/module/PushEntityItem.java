package org.jivesoftware.openfire.plugin.ibaby.module;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-16
 * Time: 上午9:41
 * To change this template use File | Settings | File Templates.
 * 推送消息体内容
 * {"xg":{"bid":0,"ts":1442987696},"messagetype":7,"aps":{"badge":1,"sound":"default","alert":"系统推送标题的前15个字"}}
 * <p/>
 * xg:bid 固定 0
 * xg.ts:时间long
 * messagetype:
 * messageId:会话ID or CacheValue
 * aps:badge 固定1
 * aps:sound 固定 default
 * aps:alert 提示消息
 */
public class PushEntityItem {

    /**
     * 免费问诊消息  1
     * 新的聊天消息  2
     * 系统消息  7
     */
    public static String NEW_QUESTION_MESSAGE = "1";
    public static String NEW_CHAT_MESSAGE = "2";
    public static String NEW_SYSTEM_MESSAGE = "7";
    public static String NEW_PULPIT_MESSAGE = "8";

    /**
     * 消息类型
     */
    private String messagetype;

    /**
     * 会话ID
     */
    private String messageId;

    /**
     * 信鸽参数
     */
    private xg xg;

    /**
     * apple 参数
     */
    private aps aps;

    public PushEntityItem() {

    }

    public PushEntityItem(String messagetype, String messageId) {
        this.messagetype = messagetype;
        this.messageId = messageId;
    }

    public PushEntityItem(String messagetype, String messageId, String ts, String alert) {
        this.messagetype = messagetype;
        this.messageId = messageId;
        xg x = new xg();
        x.setTs(ts);
        this.xg = x;
        aps a = new aps();
        a.setAlert(alert);
        this.aps = a;
    }

    public void setXGts(String ts) {
        getXg().setTs(ts);
    }

    public void setAPSAlert(String alert) {
        getAps().setAlert(alert);
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public PushEntityItem.xg getXg() {
        if (this.xg == null) {
            this.xg = new xg();
        }
        return xg;
    }

    public void setXg(PushEntityItem.xg xg) {

        this.xg = xg;
    }

    public PushEntityItem.aps getAps() {
        if (this.aps == null) {
            this.aps = new aps();
        }
        return aps;
    }

    public void setAps(PushEntityItem.aps aps) {

        this.aps = aps;
    }

    class xg {
        private String bid = "0";
        private String ts;

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }
    }

    class aps {
        private String badge = "1";
        private String sound = "default";
        private String alert;

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }
    }

}
