package com.huaylupo.cognito.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Method that contains the Authentication Response Data
 * @author ihuaylupo
 * @version 1.0
 * @since Jun 26, 2018
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

	private String accessToken;
	private String sessionToken;
	private String expiresIn;
	private String actualDate;
	private String expirationDate;
	private UserResponse userData;
	private String username;
	private String message;


	/**
	 * @param accessToken
	 */
	
	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String accessToken, String expiresIn, String sessionToken, UserResponse userData) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.sessionToken = sessionToken;
		this.userData = userData;
	}
	

	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the actualDate
	 */
	public String getActualDate() {
		return actualDate;
	}

	/**
	 * @param actualDate the actualDate to set
	 */
	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the userData
	 */
	public UserResponse getUserData() {
		return userData;
	}


	/**
	 * @param userData the userData to set
	 */
	public void setUserData(UserResponse userData) {
		this.userData = userData;
	}


	/**
	 * @return the token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param token the token to set
	 */
	public void setAccessToken(String token) {
		this.accessToken = token;
	}

	/**
	 * @return the expiresIn
	 */
	public String getExpiresIn() {
		return expiresIn;
	}

	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}


	/**
	 * @return the sessionToken
	 */
	public String getSessionToken() {
		return sessionToken;
	}


	/**
	 * @param sessionToken the sessionToken to set
	 */
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	
	
}