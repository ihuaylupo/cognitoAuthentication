package com.huaylupo.cognito.security.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huaylupo.cognito.controller.ExceptionController;
import com.huaylupo.cognito.exception.CognitoException;
import com.huaylupo.cognito.security.model.ResponseWrapper;
import com.huaylupo.cognito.util.CorsHelper;
import com.nimbusds.jose.proc.BadJOSEException;

/**
 * AWS Cognito JWT Authentication Filter - Contains the logic to call the token validation with Cognito.
 * @author ihuaylupo
 * @version 1.0
 * @since Jun 26, 2018
 */
public class AwsCognitoJwtAuthenticationFilter extends OncePerRequestFilter {


	private static final String ERROR_OCCURED_WHILE_PROCESSING_THE_TOKEN = "Error occured while processing the token";
	private static final String INVALID_TOKEN_MESSAGE = "Invalid Token";

	private static final Logger classLogger = LoggerFactory.getLogger(AwsCognitoJwtAuthenticationFilter.class);

	private AwsCognitoIdTokenProcessor awsCognitoIdTokenProcessor;
	
	 
	@Autowired
	private ApplicationContext appContext;


	public AwsCognitoJwtAuthenticationFilter(AwsCognitoIdTokenProcessor awsCognitoIdTokenProcessor) {
		this.awsCognitoIdTokenProcessor = awsCognitoIdTokenProcessor;
	}

	/**
	 * Creates an Exception Response
	 *@param responseWrapper
	 *@param request
	 *@param response
	 *@param httpStatus
	 *@throws IOException
	 * @user ihuaylupo
	 * @since 2018-06-29 
	 */
	
	private void createExceptionResponse(ServletRequest request, ServletResponse response, CognitoException exception) throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		ExceptionController exceptionController = null; 
		ObjectMapper objMapper = new ObjectMapper();
		
		//ExceptionController is now accessible because I loaded it manually
		exceptionController = appContext.getBean(ExceptionController.class);
		//Calls the exceptionController
		ResponseWrapper responseWrapper = exceptionController.handleJwtException(req, exception); 
		
		HttpServletResponse httpResponse  =  CorsHelper.addResponseHeaders(response);
		

		final HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(httpResponse);
		wrapper.setStatus(HttpStatus.UNAUTHORIZED.value());
		wrapper.setContentType(APPLICATION_JSON_VALUE);
		wrapper.getWriter().println(objMapper.writeValueAsString(responseWrapper));
		wrapper.getWriter().flush();

	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			Authentication authentication = null;
			try {
				authentication = awsCognitoIdTokenProcessor.getAuthentication((HttpServletRequest)request);

				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (BadJOSEException e) {
				SecurityContextHolder.clearContext();
				classLogger.error(e.getMessage());
				createExceptionResponse(request, response, new CognitoException(INVALID_TOKEN_MESSAGE,CognitoException.INVALID_TOKEN_EXCEPTION_CODE, e.getMessage()));
				return;
			}catch (CognitoException e) {
				SecurityContextHolder.clearContext();
				classLogger.error(e.getMessage());
				createExceptionResponse(request, response, new CognitoException(e.getErrorMessage(),CognitoException.INVALID_TOKEN_EXCEPTION_CODE, e.getDetailErrorMessage()));
				return ;
			}catch(Exception e) {
				SecurityContextHolder.clearContext();
				classLogger.error(e.getMessage());
				createExceptionResponse(request, response, new CognitoException(ERROR_OCCURED_WHILE_PROCESSING_THE_TOKEN,CognitoException.INVALID_TOKEN_EXCEPTION_CODE, e.getMessage()));
				return ;
			}
		
		filterChain.doFilter(request,response);
		
	}
}