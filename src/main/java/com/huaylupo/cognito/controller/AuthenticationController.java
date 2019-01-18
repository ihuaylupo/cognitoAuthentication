/**
 * 
 */
package com.huaylupo.cognito.controller;


import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huaylupo.cognito.model.AuthenticationRequest;
import com.huaylupo.cognito.model.AuthenticationResponse;
import com.huaylupo.cognito.model.PasswordRequest;
import com.huaylupo.cognito.model.PasswordResponse;
import com.huaylupo.cognito.model.UserRequest;
import com.huaylupo.cognito.model.UserResponse;
import com.huaylupo.cognito.model.UserSignUpRequest;
import com.huaylupo.cognito.model.UserSignUpResponse;
import com.huaylupo.cognito.service.CognitoAuthenticationService;
import com.huaylupo.cognito.util.Constants;
import com.nimbusds.jose.JOSEException;

/**
 * SpringBoot Authentication Controller
 * @author ihuaylupo
 * @version 1.0
 * @since Jun 25, 2018
 */

@RestController
@RequestMapping("auth")
public class AuthenticationController {


	@Autowired(required = false)
	private AuthenticationManager authenticationManager;

	@Autowired(required = false)
	private CognitoAuthenticationService authService;



	/**
	 * Spring Controller that has the logic to authenticate.
	 *@param authenticationRequest
	 *@return ResponseEntity<?>
	 *@throws AuthenticationException
	 *@throws IOException
	 *@throws JOSEException
	 *@user ihuaylupo
	 *@since 2018-06-28 
	 */
	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(method = POST)
	public ResponseEntity<AuthenticationResponse> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest){

		String expiresIn = null;
		String token = null;
		String accessToken = null;
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		String newPassword = authenticationRequest.getNewPassword();
		AuthenticationResponse authenticationResponse = null;


		Map <String, String> credentials = new HashMap<>();
		credentials.put(Constants.PASS_WORD_KEY, password);
		credentials.put(Constants.NEW_PASS_WORD_KEY, newPassword);

		// throws authenticationException if it fails !
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, credentials));


		Map<String,String> authenticatedCredentials = (Map<String, String>) authentication.getCredentials();
		token = authenticatedCredentials.get(Constants.ID_TOKEN_KEY);
		expiresIn = authenticatedCredentials.get(Constants.EXPIRES_IN_KEY);
		accessToken = authenticatedCredentials.get(Constants.ACCESS_TOKEN_KEY);

		UserResponse userResponse = authService.getUserInfo(accessToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		authenticationResponse = new AuthenticationResponse(token, expiresIn, accessToken, userResponse);
		authenticationResponse.setAccessToken(token);
		authenticationResponse.setExpiresIn(expiresIn);
		authenticationResponse.setSessionToken(accessToken);
		authenticationResponse.setUserData(userResponse);


		// Return the token
		return ResponseEntity.ok(new AuthenticationResponse(token, expiresIn, accessToken, userResponse));

	}



	/**
	 * SignUpRequest - Method that signs up a user into Amazon Cognito
	 *@param authenticationRequest
	 *@return ResponseEntity<User>
	 * @user ihuaylupo
	 * @since 2018-07-03 
	 */
	@RequestMapping(value="/SignUp",method=RequestMethod.POST)
	public ResponseEntity<UserSignUpResponse> signUpRequest(@RequestBody UserSignUpRequest signUpRequest){
		UserSignUpResponse user = null;

		//Calls the service that Signs up an specific User
		user = authService.signUp(signUpRequest);
		authService.signUpConfirmation(signUpRequest);
		return ResponseEntity.ok(user);

	}

	/**
	 * SignUpRequest - Method that signs up a user into Amazon Cognito
	 *@param authenticationRequest
	 *@return ResponseEntity<User>
	 * @user ihuaylupo
	 * @since 2018-07-03 
	 */
	@RequestMapping(value="/ResetPassword",method=RequestMethod.POST)
	public ResponseEntity<PasswordResponse> resetPassword(@RequestBody PasswordRequest resetPasswordRequest){

		//Calls the service that Signs up an specific User
		PasswordResponse response = authService.resetPassword(resetPasswordRequest);

		return ResponseEntity.ok(response);

	}

	
	/**
	 * SignUpRequest - Method that signs up a user into Amazon Cognito
	 *@param authenticationRequest
	 *@return ResponseEntity<User>
	 * @user ihuaylupo
	 * @since 2018-07-03 
	 */
	@RequestMapping(value="/ConfirmResetPassword",method=RequestMethod.POST)
	public ResponseEntity<PasswordResponse> confirmResetPassword(@RequestBody PasswordRequest resetPasswordRequest){

		//Calls the service that Signs up an specific User
		PasswordResponse response =  authService.confirmResetPassword(resetPasswordRequest);

		return ResponseEntity.ok(response);

	}

	/**
	 * SignUpRequest - Method that signs up a user into Amazon Cognito
	 *@param authenticationRequest
	 *@return ResponseEntity<User>
	 * @user ihuaylupo
	 * @since 2018-07-03 
	 */
	@RequestMapping(value="/SignOut",method=RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> signOut(@RequestBody AuthenticationRequest authenticationRequest){
		AuthenticationResponse response = new AuthenticationResponse();
		//Calls the service that Signs up an specific User

		String message =  authService.signOut(authenticationRequest.getAccessToken(),authenticationRequest.getUsername());

		response.setMessage(message);
		response.setUsername(authenticationRequest.getUsername());

		return ResponseEntity.ok(response);

	}




	@DeleteMapping
	public ResponseEntity<AuthenticationResponse> deleteUser(@RequestBody UserRequest userRequest){
		AuthenticationResponse response = new AuthenticationResponse();

		authService.deleteUser(userRequest.getUsername());

		response.setMessage("User Deleted");
		response.setUsername(userRequest.getUsername());
		
		return ResponseEntity.ok(response);
	}


	

}
