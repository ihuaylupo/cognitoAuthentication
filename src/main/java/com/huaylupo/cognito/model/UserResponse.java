/**
 * 
 */
package com.huaylupo.cognito.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * UserResponse.java class that contains the logic 
 * @author ihuaylupo
 * @version 
 * @since 2018-07-05 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse{ 

	
	private String username;
	private String brokerID;
	private String email;
	private String userCreateDate;
	private String userStatus;
	private String lastModifiedDate;
	private String name;
	private String lastname;
	private String companyPosition;
	private String phoneNumber;
	
	
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
	 * @return the userCreateDate
	 */
	public String getUserCreateDate() {
		return userCreateDate;
	}
	/**
	 * @param userCreateDate the userCreateDate to set
	 */
	public void setUserCreateDate(String userCreateDate) {
		this.userCreateDate = userCreateDate;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the companyPosition
	 */
	public String getCompanyPosition() {
		return companyPosition;
	}
	/**
	 * @param companyPosition the companyPosition to set
	 */
	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
