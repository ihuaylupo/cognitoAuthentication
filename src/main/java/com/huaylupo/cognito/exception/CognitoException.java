
/**
 * 
 */
package com.huaylupo.cognito.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Cognito Exception Type
 * @author ihuaylupo
 * @version 1.0
 * @since Jun 25, 2018
 */
public class CognitoException extends AuthenticationException{
	/**Generated Serial VersuiUID*/
	private static final long serialVersionUID = 5840532488004509747L;
	

	
	private static final String MODULE_CODE = "cognito";
	public static final String GENERIC_EXCEPTION_CODE = "00";
	public static final String INVALID_PASS_WORD_EXCEPTION = "01";
	public static final String ACCESS_TOKEN_MISSING_EXCEPTION = "02";
	public static final String USER_MUST_CHANGE_PASS_WORD_EXCEPTION_CODE = "03";
	public static final String USER_MUST_DO_ANOTHER_CHALLENGE = "04";
	public static final String INVALID_USERNAME_EXCEPTION = "05";
	public static final String INVALID_ACCESS_TOKEN_EXCEPTION = "06";
	public static final String EMAIL_MISSING_EXCEPTION = "07";
	public static final String NO_TOKEN_PROVIDED_EXCEPTION = "08";
	public static final String INVALID_TOKEN_EXCEPTION_CODE = "09";
	public static final String NOT_A_TOKEN_EXCEPTION = "10";

	


	private final String errorCode;
	private final String errorMessage;
	private final String detailErrorMessage;
	


	public CognitoException(String message, String code, String detail) {
		super(message);
		errorCode = code;
		errorMessage = message;
		detailErrorMessage = detail;
    }
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the detailErrorMessage
	 */
	public String getDetailErrorMessage() {
		return detailErrorMessage;
	}
	

	/**
	 * getErrorCodeByService - Returns an error code using the serviceCode and the exception Code;
	 *@param serviceCode
	 *@param exceptionCode
	 *@return errorCode
	 * @user ihuaylupo
	 * @since 2018-07-08 
	 */
	public static String getErrorCodeByService(String serviceCode, String exceptionCode) {
		String errorCode;
		
			errorCode = MODULE_CODE + serviceCode + exceptionCode;
		
		return errorCode;
	}
	

}
