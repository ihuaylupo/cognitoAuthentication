/**
 * 
 */
package com.huaylupo.cognito.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.ConfirmForgotPasswordRequest;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.amazonaws.services.cognitoidp.model.ForgotPasswordRequest;
import com.amazonaws.services.cognitoidp.model.ForgotPasswordResult;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.cognitoidp.model.GlobalSignOutRequest;
import com.amazonaws.services.cognitoidp.model.UserType;
import com.huaylupo.cognito.authentication.AWSClientProviderBuilder;
import com.huaylupo.cognito.exception.CognitoException;
import com.huaylupo.cognito.model.AuthenticationRequest;
import com.huaylupo.cognito.model.PasswordRequest;
import com.huaylupo.cognito.model.PasswordResponse;
import com.huaylupo.cognito.model.UserResponse;
import com.huaylupo.cognito.model.UserSignUpRequest;
import com.huaylupo.cognito.model.UserSignUpResponse;
import com.huaylupo.cognito.security.model.SpringSecurityUser;
import com.huaylupo.cognito.util.AWSConfig;
import com.huaylupo.cognito.util.StringValidationHelper;




/**
 * CognitoAuthenticationService.java class that contains the logic to connect to Cognito using Username and password.
 * @author ihuaylupo (1.0)
 * @version 1.0 
 * @since 2018-07-03 
 */
@Service
public class CognitoAuthenticationService {

	/**New password key*/
	private static final String NEW_PASS_WORD = "NEW_PASSWORD";
	/**New password required challenge key*/
	private static final String NEW_PASS_WORD_REQUIRED = "NEW_PASSWORD_REQUIRED";
	/**Password key*/
	private static final String PASS_WORD = "PASSWORD";
	/**Username key*/
	private static final String USERNAME = "USERNAME";



	private final Logger classLogger = LoggerFactory.getLogger(this.getClass());


	@Autowired 
	AWSClientProviderBuilder cognitoBuilder;

	@Autowired
	private AWSConfig cognitoConfig;



	/**
	 * getAmazonCognitoIdentityClient 
	 *@return
	 * @user ihuaylupo
	 * @since 2018-07-31 
	 */
	private AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {

		return cognitoBuilder.getAWSCognitoIdentityClient();

	}

	

	/**
	 * Method that contains the logic of authentication with AWS Cognito.
	 *@param authenticationRequest
	 *@return SpringSecurityUser 
	 * @user ihuaylupo(1.0)
	 */
	public  SpringSecurityUser authenticate(AuthenticationRequest authenticationRequest){

		AuthenticationResultType authenticationResult = null;
		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();


		try {

			final Map<String, String> authParams = new HashMap<>();
			authParams.put(USERNAME, authenticationRequest.getUsername());
			authParams.put(PASS_WORD, authenticationRequest.getPassword());

			final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
			authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
			.withClientId(cognitoConfig.getClientId())
			.withUserPoolId(cognitoConfig.getPoolId())
			.withAuthParameters(authParams);

			AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);


			//Has a Challenge
			if(StringUtils.isNotBlank(result.getChallengeName())) {

				//If the challenge is required new Password validates if it has the new password variable.
				if(NEW_PASS_WORD_REQUIRED.equals(result.getChallengeName())){

					if(null == authenticationRequest.getNewPassword()) {
						throw new CognitoException("User must provide a new password", CognitoException.USER_MUST_CHANGE_PASS_WORD_EXCEPTION_CODE, result.getChallengeName());
					}else {
						//we still need the username

						final Map<String, String> challengeResponses = new HashMap<>();
						challengeResponses.put(USERNAME, authenticationRequest.getUsername());
						challengeResponses.put(PASS_WORD, authenticationRequest.getPassword());

						//add the new password to the params map
						challengeResponses.put(NEW_PASS_WORD, authenticationRequest.getNewPassword());

						//populate the challenge response
						final AdminRespondToAuthChallengeRequest request = new AdminRespondToAuthChallengeRequest();
						request.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
						.withChallengeResponses(challengeResponses)
						.withClientId(cognitoConfig.getClientId())
						.withUserPoolId(cognitoConfig.getPoolId())
						.withSession(result.getSession());

						AdminRespondToAuthChallengeResult resultChallenge = cognitoClient.adminRespondToAuthChallenge(request);
						authenticationResult = resultChallenge.getAuthenticationResult();

					}
				}else {
					//has another challenge
					throw new CognitoException(result.getChallengeName(), CognitoException.USER_MUST_DO_ANOTHER_CHALLENGE, result.getChallengeName());
				}

			}else {
				//Doesn't have a challenge
				authenticationResult = result.getAuthenticationResult();
			}

			//AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_MANAGER")
			SpringSecurityUser userAuthenticated = new SpringSecurityUser(authenticationRequest.getUsername(),authenticationRequest.getPassword(),null,null,null);
			userAuthenticated.setAccessToken(authenticationResult.getAccessToken());
			userAuthenticated.setExpiresIn(authenticationResult.getExpiresIn());
			userAuthenticated.setTokenType(authenticationResult.getTokenType());
			userAuthenticated.setRefreshToken(authenticationResult.getRefreshToken());
			userAuthenticated.setIdToken(authenticationResult.getIdToken());


			if(classLogger.isInfoEnabled()) {
				classLogger.info("User successfully authenticated userInfo: username {}", authenticationRequest.getUsername());
			}


			return userAuthenticated;
		}catch(com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(),e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch (CognitoException cognitoException) {
			throw cognitoException;
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}

	}

	/**
	 * getUserInfo - Returns the data of the specified user.
	 *@param accessToken
	 *@return userResponse - object that contains all the cognito data.
	 * @user ihuaylupo
	 * @since 2018-08-09 
	 */
	public UserResponse getUserInfo(String accessToken) {
		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

		try {

			if (StringUtils.isBlank(accessToken)){
				throw new CognitoException("User must provide an access token",CognitoException.INVALID_ACCESS_TOKEN_EXCEPTION, "User must provide an access token");
			}

			GetUserRequest userRequest = new GetUserRequest()
					.withAccessToken(accessToken);

			GetUserResult userResult = cognitoClient.getUser(userRequest);

			List<AttributeType> userAttributes = userResult.getUserAttributes();
			UserResponse userResponse = getUserAttributesData(userAttributes, userResult.getUsername());

			return userResponse;


		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}

	}

	/**
	 * getUserAttributesData 
	 *@param userResult
	 *@param username
	 *@return userResponse - Object filled with all the user data.
	 * @user ihuaylupo
	 * @since 2018-08-09 
	 */
	private UserResponse getUserAttributesData(List<AttributeType> userAttributes, String username) {
		UserResponse userResponse = new UserResponse();

		userResponse.setUsername(username);

		for(AttributeType attribute : userAttributes) {
			if(attribute.getName().equals("email")) {
				userResponse.setEmail(attribute.getValue());
			}else if(attribute.getName().equals("phone_number")) {
				userResponse.setPhoneNumber(attribute.getValue());
			}else if(attribute.getName().equals("name")) {
				userResponse.setName(attribute.getValue());
			}else if(attribute.getName().equals("family_name")) {
				userResponse.setLastname(attribute.getValue());
			}else if(attribute.getName().equals("custom:companyPosition")) {
				userResponse.setCompanyPosition(attribute.getValue());
			}
		}

		return userResponse;
	}

	/**
	 * SignUp - Method that contains the logic to Sign Up a specific user into Amazon Cognito.
	 *@param signUpRequest
	 *@return UserSignUpResponse
	 * @user ihuaylupo
	 * @since 2018-07-03 
	 */
	public UserSignUpResponse signUp(UserSignUpRequest signUpRequest){

		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

		//If the username is empty we should put as username the email. Later on the
		//validate SignUpRequiredFields we are going to validate the email format.
		if(StringUtils.isEmpty(signUpRequest.getUsername())) {
			signUpRequest.setUsername(signUpRequest.getEmail());
		}

		if(classLogger.isInfoEnabled()) {
			classLogger.info("creating user {}", signUpRequest.getEmail());
		}

		try {

			AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest()
					.withUserPoolId(cognitoConfig.getPoolId())
					.withUsername(signUpRequest.getUsername())
					.withUserAttributes(
							new AttributeType()
							.withName("email")
							.withValue(signUpRequest.getEmail()),
							new AttributeType()
							.withName("name")
							.withValue(signUpRequest.getName()),
							new AttributeType()
							.withName("family_name")
							.withValue(signUpRequest.getLastname()),
							new AttributeType()
							.withName("phone_number")
							.withValue(signUpRequest.getPhoneNumber()),
							new AttributeType()
							.withName("custom:brokerID")
							.withValue(signUpRequest.getBrokerID()),
							new AttributeType()
							.withName("custom:companyPosition")
							.withValue(signUpRequest.getCompanyPosition()),
							new AttributeType()
							.withName("email_verified")
							.withValue("true"))
					.withTemporaryPassword("QuhxmE472.")
					.withMessageAction("SUPPRESS")
					.withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
					.withForceAliasCreation(Boolean.FALSE);

					AdminCreateUserResult createUserResult =  cognitoClient.adminCreateUser(cognitoRequest);
		
					UserSignUpResponse userResult = new UserSignUpResponse();
					UserType cognitoUser =  createUserResult.getUser();
		
					userResult.setUsername(signUpRequest.getUsername());
					userResult.setEmail(signUpRequest.getEmail());
					userResult.setEnabled(cognitoUser.getEnabled());
					userResult.setUserStatus(cognitoUser.getUserStatus());
					userResult.setLastModifiedDate(StringValidationHelper.convertDateToString(cognitoUser.getUserLastModifiedDate(),"MM-dd-yyyy"));
					userResult.setUserCreatedDate(StringValidationHelper.convertDateToString(cognitoUser.getUserCreateDate(),"MM-dd-yyyy"));
					userResult.setBrokerID(signUpRequest.getBrokerID());

			if(classLogger.isInfoEnabled()) {
				classLogger.info("User created {} " , userResult);
			}
			

			return userResult;

		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch (CognitoException cognitoException) {
			throw cognitoException;
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}


	}
	
	/**
	 * SignUpConfirm - Confirms the user sign up for a specific user in Amazon Cognito.
	 *@param signUpRequest
	 * @user ihuaylupo
	 * @since 2018-07-03 
	 */
	public void signUpConfirmation(UserSignUpRequest signUpRequest){
		String temporaryPassword = "QuhxmE472.";

		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();


		if (StringUtils.isBlank(signUpRequest.getEmail())){
			throw new CognitoException("Invalid email", CognitoException.EMAIL_MISSING_EXCEPTION, "Invalid email");
		}

		if (StringUtils.isBlank(signUpRequest.getPassword())){
			throw new CognitoException("Invalid Password",CognitoException.INVALID_PASS_WORD_EXCEPTION, "Invalid password");
		}

		if(classLogger.isInfoEnabled()) {
			classLogger.info("confirming signup of user {}", signUpRequest.getEmail());
		}

		try{

			//First Attempt must attempt signin with temporary password in order to establish session for password change
			Map<String,String> initialParams = new HashMap<>();
			initialParams.put(USERNAME, signUpRequest.getUsername());
			initialParams.put(PASS_WORD, temporaryPassword);

			//Initializes the request.
			AdminInitiateAuthRequest initialRequest = new AdminInitiateAuthRequest()
					.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
					.withAuthParameters(initialParams)
					.withClientId(cognitoConfig.getClientId())
					.withUserPoolId(cognitoConfig.getPoolId());

			//Invokes the cognito authentication
			AdminInitiateAuthResult initialResponse = cognitoClient.adminInitiateAuth(initialRequest);

			//Validates if it has a new password.
			if (! ChallengeNameType.NEW_PASSWORD_REQUIRED.name().equals(initialResponse.getChallengeName())){
				throw new CognitoException(initialResponse.getChallengeName(), CognitoException.USER_MUST_DO_ANOTHER_CHALLENGE, initialResponse.getChallengeName());
			}

			Map<String,String> challengeResponses = new HashMap<>();
			challengeResponses.put(USERNAME, signUpRequest.getUsername());
			challengeResponses.put(PASS_WORD, temporaryPassword);
			challengeResponses.put(NEW_PASS_WORD, signUpRequest.getPassword());

			//Initializes the challenge response
			AdminRespondToAuthChallengeRequest finalRequest = new AdminRespondToAuthChallengeRequest()
					.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
					.withChallengeResponses(challengeResponses)
					.withClientId(cognitoConfig.getClientId())
					.withUserPoolId(cognitoConfig.getPoolId())
					.withSession(initialResponse.getSession());

			//Invokes the cognito authentication
			AdminRespondToAuthChallengeResult challengeResponse = cognitoClient.adminRespondToAuthChallenge(finalRequest);

			//Validates if it has another challenge
			if (!StringUtils.isBlank(challengeResponse.getChallengeName())){
				throw new CognitoException(challengeResponse.getChallengeName(), CognitoException.USER_MUST_DO_ANOTHER_CHALLENGE, challengeResponse.getChallengeName());
			}

			//Adds user to the developer group

			addUserToGroup(signUpRequest.getUsername(), cognitoConfig.getDeveloperGroup());

			if(classLogger.isInfoEnabled()) {
				classLogger.info("Sign up confirm successfully for user {} " , signUpRequest.getUsername());
			}


		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch (CognitoException cognitoException) {
			throw cognitoException;
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}

	}
	
	/**
	 * addUserToGroup - Adds an specific user to an specific group
	 *@param username
	 *@param groupname
	 * @user ihuaylupo
	 * @since 2018-07-04 
	 */
	public void addUserToGroup(String username, String groupname){
		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

		if(classLogger.isInfoEnabled()) {
			classLogger.info(String.format("Adding user  %1$s, to %2$s group", username, groupname));
		}

		try {

			AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
					.withGroupName(groupname)
					.withUserPoolId(cognitoConfig.getPoolId())
					.withUsername(username);

			cognitoClient.adminAddUserToGroup(addUserToGroupRequest);

			if(classLogger.isInfoEnabled()) {
				classLogger.info(String.format("User  %1$s added to %2$s group", username , groupname));
			}

		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}

	}
	
	/**
	 * deleteUser - Deletes an specific User. USED only for the automated tests.
	 *@param user
	 * @user ihuaylupo
	 * @since 2018-07-16 
	 */
	public void deleteUser(String user){
		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

		if(classLogger.isInfoEnabled()) {
			classLogger.info("Delete user {}", user);
		}

		try {

			AdminDeleteUserRequest deleteAdminUserRequest = new AdminDeleteUserRequest()
					.withUserPoolId(cognitoConfig.getPoolId())
					.withUsername(user);

			cognitoClient.adminDeleteUser(deleteAdminUserRequest);

			if(classLogger.isInfoEnabled()) {
				classLogger.info("User deleted {}" , user );
			}

		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}
	}
	
	/**
	 * SignOut - Signs out from an AWS Account
	 *@param accessToken
	 *@param username
	 *@return resultMessage
	 * @user ihuaylupo
	 * @since 2018-07-05 
	 */
	public String signOut(String accessToken, String username){
		String resultMessage = null;
		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

		if(classLogger.isInfoEnabled()) {
			classLogger.info("User sign out request {}", username);
		}

		try {

			if(null != accessToken) {
				GlobalSignOutRequest globalSignOutRequest = new GlobalSignOutRequest()
						.withAccessToken(accessToken);

				cognitoClient.globalSignOut(globalSignOutRequest);
				resultMessage = "SUCCESS";

				if(classLogger.isInfoEnabled()) {
					classLogger.info("User signed out {}", username);
				}

				return resultMessage;

			}else {
				throw new CognitoException("Missing access token", CognitoException.ACCESS_TOKEN_MISSING_EXCEPTION,"Missing access token");
			}


		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch (CognitoException cognitoException) {
			throw cognitoException;
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}
	}
	
	/**
	 * ResetPassword - Method that contains the logic to send reset password code for a Amazon Cognito user.
	 *@param passwordRequest
	 *@return PasswordResponse
	 * @user ihuaylupo
	 * @since 2018-07-04 
	 */

	public PasswordResponse resetPassword(PasswordRequest passwordRequest){
		String username = passwordRequest.getUsername();
		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

		if(classLogger.isInfoEnabled()) {
			classLogger.info("Reset password {}", username);
		}

		try {

			//If username is blank it throws an error
			if (StringUtils.isBlank(username)) {
				throw new CognitoException("Invalid username", CognitoException.INVALID_USERNAME_EXCEPTION, "Invalid username");
			}

			ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest()
					.withClientId(cognitoConfig.getClientId())
					.withUsername(username);

			ForgotPasswordResult forgotPasswordResult =  cognitoClient.forgotPassword(forgotPasswordRequest);

			if(classLogger.isInfoEnabled()) {
				classLogger.info("Reset password response Delivery Details: {} ",forgotPasswordResult.getCodeDeliveryDetails());
			}

			PasswordResponse passwordResponse = new PasswordResponse();
			passwordResponse.setDestination(forgotPasswordResult.getCodeDeliveryDetails().getDestination());
			passwordResponse.setDeliveryMedium(forgotPasswordResult.getCodeDeliveryDetails().getDeliveryMedium());
			passwordResponse.setUsername(username);
			passwordResponse.setMessage("SUCCESS");


			return passwordResponse;

		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch(CognitoException e) {
			classLogger.error(e.getMessage(), e);
			throw e;
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}

	}
	
	/**
	 * confirmResetPassword - Method that contains that allows a user to enter a confirmation code to reset a forgotten password.
	 *@param passwordRequest
	 *@return PasswordResponse
	 * @user ihuaylupo
	 * @since 2018-07-04 
	 */

	public PasswordResponse confirmResetPassword(PasswordRequest passwordRequest){
		String username = passwordRequest.getUsername();
		AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();



		if(classLogger.isInfoEnabled()) {
			classLogger.info("Confirm Reset Password {}", username);
		}

		try {

			ConfirmForgotPasswordRequest forgotPasswordRequest = new ConfirmForgotPasswordRequest()
					.withClientId(cognitoConfig.getClientId())
					.withUsername(username)
					.withPassword(passwordRequest.getPassword())
					.withConfirmationCode(passwordRequest.getConfirmationCode());

			cognitoClient.confirmForgotPassword(forgotPasswordRequest);

			PasswordResponse passwordResponse = new PasswordResponse();
			passwordResponse.setUsername(username);
			passwordResponse.setMessage("SUCCESS");



			if(classLogger.isInfoEnabled()) {
				classLogger.info("Confirm Reset Password successful for {} ", username);
			}

			return passwordResponse;


		}catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e){
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		}catch(CognitoException e) {
			throw e;
		}catch(Exception e) {
			classLogger.error(e.getMessage(), e);
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE,e.getMessage());
		}

	}
}
