/**
 * 
 */
package com.huaylupo.cognito.model;

/**
 * AWSUser.java class that contains the logic 
 * @author ihuaylupo
 * @version 
 * @since 2018-07-03 
 */
public class UserSignUpRequest {
	
	
	private String username;
	private String password;
	private String email;
	private String brokerID;
	private String name;
	private String lastname;
	private String phoneNumber;
	private String companyPosition;
	private String agreementFlag; 
	
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
	 * @return the agreementFlag
	 */
	public String getAgreementFlag() {
		return agreementFlag;
	}
	/**
	 * @param agreementFlag the agreementFlag to set
	 */
	public void setAgreementFlag(String agreementFlag) {
		this.agreementFlag = agreementFlag;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserSignUpRequest [username=" + username + ", email=" + email + ", brokerID=" + brokerID + ", name="
				+ name + ", lastname=" + lastname + ", phoneNumber=" + phoneNumber + ", companyPosition="
				+ companyPosition + "]";
	}
	
	
	

}
