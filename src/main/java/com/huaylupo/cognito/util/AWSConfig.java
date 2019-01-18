/**
 * 
 */
package com.huaylupo.cognito.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author ihuaylupo
 * @version
 * @since Jun 25, 2018
 */

@Configuration
@PropertySource("classpath:aws.properties")
@ConfigurationProperties
public class AWSConfig {

	private static final String COGNITO_IDENTITY_POOL_URL = "https://cognito-idp.%s.amazonaws.com/%s";
	private static final String JSON_WEB_TOKEN_SET_URL_SUFFIX = "/.well-known/jwks.json";

	private String clientId;
	private String poolId;
	private String endpoint;
	private String region;
	private String identityPoolId;
	private String developerGroup;


	
	private String userNameField = "cognito:username";
	private String groupsField = "cognito:groups";
	private int connectionTimeout = 2000;
	private int readTimeout = 2000;
	private String httpHeader = "Authorization";

	public String getJwkUrl() {
		StringBuilder cognitoURL = new StringBuilder();
		cognitoURL.append(COGNITO_IDENTITY_POOL_URL);
		cognitoURL.append(JSON_WEB_TOKEN_SET_URL_SUFFIX);
		return String.format( cognitoURL.toString(),region,poolId);
	}

	public String getCognitoIdentityPoolUrl() {
		return String.format(COGNITO_IDENTITY_POOL_URL,region,poolId);
	}


	/**
	 * @return the userNameField
	 */
	public String getUserNameField() {
		return userNameField;
	}

	/**
	 * @return the groupsField
	 */
	public String getGroupsField() {
		return groupsField;
	}


	/**
	 * @return the connectionTimeout
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}


	/**
	 * @return the readTimeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}


	/**
	 * @return the httpHeader
	 */
	public String getHttpHeader() {
		return httpHeader;
	}

	/**
	 * @return the identityPoolId
	 */
	public String getIdentityPoolId() {
		return identityPoolId;
	}
	/**
	 * @param identityPoolId the identityPoolId to set
	 */
	public void setIdentityPoolId(String identityPoolId) {
		this.identityPoolId = identityPoolId;
	}
	
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the poolId
	 */
	public String getPoolId() {
		return poolId;
	}
	/**
	 * @param poolId the poolId to set
	 */
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}
	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the developerGroup
	 */
	public String getDeveloperGroup() {
		return developerGroup;
	}

	/**
	 * @param developerGroup the developerGroup to set
	 */
	public void setDeveloperGroup(String developerGroup) {
		this.developerGroup = developerGroup;
	}


}
