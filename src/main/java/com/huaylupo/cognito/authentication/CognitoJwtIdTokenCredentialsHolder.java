package com.huaylupo.cognito.authentication;


/**
 * Bean that holds the IDToken associated with a specify user.
 * @author ihuaylupo
 * @version
 * @since Jun 26, 2018
 */
public class CognitoJwtIdTokenCredentialsHolder {

	private String idToken;
	
    public String getIdToken() {
        return idToken;
    }

    public CognitoJwtIdTokenCredentialsHolder setIdToken(String idToken) {
        this.idToken = idToken;
        return this;
    }

   
}
