package com.red5.ibaby.pulpit;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-1-29
 * Time: 下午5:58
 * 同步用户在线情况
 */
public class PulpitUser {

    //讲坛编号
    private String pulpitId;

    //用户编号
    private String userId;

    // 状态【0不在线，1在线】
    private int state = 0;

    public PulpitUser(String pulpitId, String userId, int state) {
        this.pulpitId = pulpitId;
        this.userId = userId;
        this.state = state;
    }

    public String getPulpitId() {
        return pulpitId.replace("pulpit/","");
    }

    public void setPulpitId(String pulpitId) {
        this.pulpitId = pulpitId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public String toString() {
        StringBuffer sbf = new StringBuffer("");

        sbf.append("pulpitId=" + this.pulpitId + "");
        sbf.append("&userId=\"" + this.userId+"");
        sbf.append("&state=\"" + this.state+"\"");
        return sbf.toString();
    }
}
