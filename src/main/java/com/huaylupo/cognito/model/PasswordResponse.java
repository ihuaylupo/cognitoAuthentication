/**
 * 
 */
package com.huaylupo.cognito.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * PasswordResponse.java class that contains the logic 
 * @author ihuaylupo
 * @version 
 * @since 2018-07-04 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordResponse {
	
	private String destination;
	private String deliveryMedium;
	private String message;
	private String username;
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the deliveryMedium
	 */
	public String getDeliveryMedium() {
		return deliveryMedium;
	}
	/**
	 * @param deliveryMedium the deliveryMedium to set
	 */
	public void setDeliveryMedium(String deliveryMedium) {
		this.deliveryMedium = deliveryMedium;
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
