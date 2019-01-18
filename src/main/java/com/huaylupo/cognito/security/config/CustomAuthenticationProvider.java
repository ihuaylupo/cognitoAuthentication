/**
 * 
 */
package com.huaylupo.cognito.security.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.huaylupo.cognito.model.AuthenticationRequest;
import com.huaylupo.cognito.security.model.SpringSecurityUser;
import com.huaylupo.cognito.service.CognitoAuthenticationService;
import com.huaylupo.cognito.util.Constants;

/**
 * Custom Authentication that manipulates the logic to call the authentication with Cognito.
 * @author ihuaylupo
 * @version
 * @since Jun 26, 2018
 */
@Component
public class CustomAuthenticationProvider
implements AuthenticationProvider {

	@Autowired
	CognitoAuthenticationService cognitoService;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Authentication authenticate(Authentication authentication) {
		AuthenticationRequest authenticationRequest = null;


		if(null != authentication) {
			authenticationRequest = new AuthenticationRequest();
			Map <String,String> credentials = (Map<String, String>) authentication.getCredentials();
			authenticationRequest.setNewPassword(credentials.get(Constants.NEW_PASS_WORD_KEY));
			authenticationRequest.setPassword(credentials.get(Constants.PASS_WORD_KEY));
			authenticationRequest.setUsername(authentication.getName());
			
			
			SpringSecurityUser userAuthenticated = cognitoService.authenticate(authenticationRequest);
			if (null != userAuthenticated) {
				// use the credentials
				// and authenticate against the third-party system
				
				Map <String, String> authenticatedCredentials = new HashMap<>();
				authenticatedCredentials.put(Constants.ACCESS_TOKEN_KEY, userAuthenticated.getAccessToken());
				authenticatedCredentials.put(Constants.EXPIRES_IN_KEY, userAuthenticated.getExpiresIn().toString());
				authenticatedCredentials.put(Constants.ID_TOKEN_KEY, userAuthenticated.getIdToken());
				authenticatedCredentials.put(Constants.PASS_WORD_KEY, userAuthenticated.getPassword());
				authenticatedCredentials.put(Constants.REFRESH_TOKEN_KEY, userAuthenticated.getRefreshToken());
				authenticatedCredentials.put(Constants.TOKEN_TYPE_KEY, userAuthenticated.getTokenType());
				return new UsernamePasswordAuthenticationToken(
						userAuthenticated.getUsername(), authenticatedCredentials, userAuthenticated.getAuthorities());
			} else {
				return null;
			}
		}else {
			throw new UsernameNotFoundException(String.format("No appUser found with username '%s'.", ""));
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(
				UsernamePasswordAuthenticationToken.class);
	}
	
	
}

