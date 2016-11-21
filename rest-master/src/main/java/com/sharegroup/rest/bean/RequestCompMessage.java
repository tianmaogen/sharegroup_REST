package com.sharegroup.rest.bean;

/**
 * .订阅号向用户发送消息
 * Created by Administrator on 2015/12/7.
 * <p/>
 * <message type=chat from= to= id=消息id>
 * <subject>comp</subject>
 * <body>
 * {
 * ChatType: 10,
 * CompId: 订阅号的id,
 * CompName: 订阅号的名称,
 * IconPic: 订阅号的图片,
 * CompNewsId: 消息id,
 * NewsTitle: 消息的标题,
 * AddTime: 消息的创建时间,
 * }
 * </body>
 * </message>
 */
public class RequestCompMessage {
    //10 订阅号
    private int ChatType;
    //订阅号的id,
    private String CompId;
    //订阅号的名称
    private String CompName;
    //订阅号的图片
    private String IconPic;
    //消息id
    private String CompNewsId;
    // 消息的标题
    private String NewsTitle;
    //消息的创建时间
    private String AddTime;

    public int getChatType() {
        return ChatType;
    }

    public void setChatType(int chatType) {
        ChatType = chatType;
    }

    public String getCompId() {
        return CompId;
    }

    public void setCompId(String compId) {
        CompId = compId;
    }

    public String getCompName() {
        return CompName;
    }

    public void setCompName(String compName) {
        CompName = compName;
    }

    public String getIconPic() {
        return IconPic;
    }

    public void setIconPic(String iconPic) {
        IconPic = iconPic;
    }

    public String getCompNewsId() {
        return CompNewsId;
    }

    public void setCompNewsId(String compNewsId) {
        CompNewsId = compNewsId;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }
}
