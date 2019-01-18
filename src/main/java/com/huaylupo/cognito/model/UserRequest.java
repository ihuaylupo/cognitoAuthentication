/**
 * 
 */
package com.huaylupo.cognito.model;

/**
 * UserRequest.java class that contains the logic 
 * @author ihuaylupo
 * @version 
 * @since 2018-08-09 
 */
public class UserRequest {

	
	private String accessToken;
	private String username;

	
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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

	
	
}
