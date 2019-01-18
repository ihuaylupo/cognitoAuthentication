/**
 * 
 */
package com.huaylupo.cognito.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.huaylupo.cognito.util.AWSConfig;

/**
 * AWSClientProviderBuilder.java class that contains the logic 
 * @author ihuaylupo
 * @version 
 * @since 2018-07-31 
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AWSClientProviderBuilder {

	@Autowired
	private  AWSConfig cognitoConfig;

	private AWSCognitoIdentityProvider cognitoIdentityProvider;
	private AmazonS3 s3Client;
	private ClasspathPropertiesFileCredentialsProvider propertiesFileCredentialsProvider;
	private String region;
	/**
	 * getAWSCognitoIdentityClient 
	 *@return
	 * @user ihuaylupo
	 * @since 2018-07-31 
	 */

	private void initCommonInfo() {
		if(null == propertiesFileCredentialsProvider) {
			propertiesFileCredentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		}
		if(null == region) {
			region = cognitoConfig.getRegion();
		}
	}

	public AWSCognitoIdentityProvider getAWSCognitoIdentityClient() {
		if( null == cognitoIdentityProvider) {
			initCommonInfo();

			cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
					.withCredentials(propertiesFileCredentialsProvider)
					.withRegion(region)
					.build();
		}

		return cognitoIdentityProvider;
	}

	/** 
	 * Returns the Amazon S3 Client.
	 * @return s3Client - AmazonS3
	 */
	public AmazonS3 getAWSS3Client() {
		if( null == s3Client) {
			initCommonInfo();

			s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(propertiesFileCredentialsProvider)
					.withRegion(region)
					.build();

		}
		return s3Client;
	}
}
