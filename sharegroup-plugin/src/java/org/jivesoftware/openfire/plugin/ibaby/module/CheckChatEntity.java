package org.jivesoftware.openfire.plugin.ibaby.module;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public class CheckChatEntity {

    private String chatType;
    private String toUser;
    private String fromUser;
    private String diagnosisId;

    @Override
    public String toString() {
        StringBuffer sbf = new StringBuffer("?");

        sbf.append("chatType="+this.chatType);
        sbf.append("&toUser="+this.toUser);
        sbf.append("&fromUser="+this.fromUser);
        sbf.append("&diagnosisId="+this.diagnosisId);
        return sbf.toString();
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}
