package com.huaylupo.cognito.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

/**
 * Custom authentication provider that will manage the cognito token authentication.
 * @author ihuaylupo
 * @version 1.0
 * @since Jun 26, 2018
 */
public class CognitoJwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication){
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
