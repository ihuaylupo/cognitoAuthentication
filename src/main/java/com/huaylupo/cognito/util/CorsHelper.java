/**
 * 
 */
package com.huaylupo.cognito.util;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * CORSHelper.java class that contains the logic of implementing CORS response headers.
 * @author ihuaylupo
 * @version 
 * @since 2018-09-12 
 */
public class CorsHelper {
	
	
	public static HttpServletResponse addResponseHeaders(ServletResponse res) {
		
		HttpServletResponse httpResponse = (HttpServletResponse) res;
		
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Allow-Headers", "content-type,Authorization");
		
		return httpResponse;
		
	}

}
