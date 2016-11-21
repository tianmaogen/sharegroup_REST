package com.sharegroup.rest.exception;

/**
 * 自定义的异常
 * @author xiangxj
 *
 */
public class GeneralException extends CustomException {
	/**
	 *版本号
	 */
	private static final long serialVersionUID = 1L;

	public GeneralException(String message, Object... parames) {
        super(message, "General", parames);
	}

}
