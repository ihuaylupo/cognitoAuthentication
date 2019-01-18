/**
 * 
 */
package com.huaylupo.cognito.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * AWSUser.java class that contains the logic 
 * @author ihuaylupo
 * @version 
 * @since 2018-07-03 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignUpResponse {
	
	
	private String username;
	private String userCreatedDate;
	private String lastModifiedDate;
	private boolean enabled;
	private String userStatus;
	private String password;
	private String email;
	private String brokerID;
	
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
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}
	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the userCreatedDate
	 */
	public String getUserCreatedDate() {
		return userCreatedDate;
	}
	/**
	 * @param userCreatedDate the userCreatedDate to set
	 */
	public void setUserCreatedDate(String userCreatedDate) {
		this.userCreatedDate = userCreatedDate;
	}
	/**
	 * @return the lastModifiedDate
	 */
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	/**
	 * @return the brokerID
	 */
	public String getBrokerID() {
		return brokerID;
	}
	/**
	 * @param brokerID the brokerID to set
	 */
	public void setBrokerID(String brokerID) {
		this.brokerID = brokerID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserSignUpResponse [username=" + username + ", userCreatedDate=" + userCreatedDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", enabled=" + enabled + ", userStatus=" + userStatus
				+ ", email=" + email + ", brokerID=" + brokerID + "]";
	}
	
	

}
