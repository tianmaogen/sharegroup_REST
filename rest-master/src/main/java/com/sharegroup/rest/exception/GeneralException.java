package com.sharegroup.rest.exception;

/**
 * �Զ�����쳣
 * @author xiangxj
 *
 */
public class GeneralException extends CustomException {
	/**
	 *�汾��
	 */
	private static final long serialVersionUID = 1L;

	public GeneralException(String message, Object... parames) {
        super(message, "General", parames);
	}

}
