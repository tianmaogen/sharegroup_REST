package com.sharegroup.ibaby.Module;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 上午9:53
 * To change this template use File | Settings | File Templates.
 * .net接口返回消息
 */
public class ResultMessageNET {

    //执行状态
    private Boolean Success;
    //返回消息
    private String Msg;
    //
    private Object Data;
    //执行结果 200 为正常
    private Integer Code;

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    @Override
    public String toString() {
        return "ResultMessageNET{" +
                "Success=" + Success +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                ", Code=" + Code +
                '}';
    }
}
