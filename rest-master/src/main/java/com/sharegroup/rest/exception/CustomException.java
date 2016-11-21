package com.sharegroup.rest.exception;

/**
 * �Զ�����쳣����
 * @author Jove
 *
 */
public abstract class CustomException extends Exception {
	/**
	 *�汾��
	 */
	private static final long serialVersionUID = 1L;
	private String code;

	private Object[] parames;

	public CustomException(String message, String code, Object... parames) {
		super(message);
		this.code = code;
		this.parames = parames;
	}

    public String getCode() {
        return code;
    }

    public Object[] getParames() {
        return parames;
    }

    public String toString(){
        return super.toString();
    }
}
