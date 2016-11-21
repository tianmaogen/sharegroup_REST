/**   
* @Title: ReturnResult.java 
* @Package�? com.appvworks.framework.Utils 
* @Description: TODO
* @author：duanwei 
* @date�? 2015�?7�?2�? 上午10:43:32  
*/
package com.sharegroup.rest.utils;

import java.io.Serializable;


public class ReturnResult implements Serializable { 
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS=200;
	public static final int FAILURE=300;
	public static final int FAULURE_INTERNAL_ERROR=301;
	public static final int FAULURE_PARAMTER_REQUIRED=302;
	public static final int FAULURE_USER_NOT_LOGIN=401;
	public static final int FAULURE_URL_DIGTAL_ERROR=501;
	
	private int code;
	private String description;
	private Object data;
	
	public Object getData() {
		return this.data;
	}
	
	/**
	 * @param obj 无用的参数，防止在JSON序列化时调用此方法，调用时请置为NULL.
	 * @return
	 */
	public Object getData(Object obj) {
		return this.data;
	}
	public void setData(Object obj) {
		this.data = obj;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static ReturnResult SUCCESS(){
		return SUCCESS("成功");
	}
	public static ReturnResult SUCCESS(Object Data){
		return SUCCESS("成功",Data);
	}
	/**
	 * @param msg 成功信息
	 * @return
	 */
	public static ReturnResult SUCCESS(String msg){
		ReturnResult rp=new ReturnResult();
		rp.setCode(SUCCESS);
		rp.description=msg;
		return rp;
	}
	
	/**
	 * @param msg 成功信息
	 * @return
	 */
	public static ReturnResult SUCCESS(int code,Object data){
		ReturnResult rp=new ReturnResult();
		rp.setCode(code);
		rp.description="成功";
		rp.setData(data);
		return rp;
	}
	/**
	 * @param msg  成功信息
	 * @param data 返回数据
	 * @return
	 */
	public static ReturnResult SUCCESS(String msg,Object data){
		ReturnResult rp=new ReturnResult();
		rp.setCode(SUCCESS);
		rp.description=msg;
		rp.setData(data);
		return rp;
	}
	
	/** 
	 * @param msg 错误消息
	 * @return
	 */
	public static ReturnResult FAILUER(String msg){
		ReturnResult rp=new ReturnResult();
		rp.setCode(FAILURE);
		rp.description=msg;
		return rp;
	}
	/**
	 * @param failureCode 错误编码
	 * @param msg 错误消息
	 * @return
	 */
	public static ReturnResult FAILUER(int failureCode,String msg){
		ReturnResult rp=FAILUER(msg);
		rp.code=failureCode;
		return rp;
	}
}
