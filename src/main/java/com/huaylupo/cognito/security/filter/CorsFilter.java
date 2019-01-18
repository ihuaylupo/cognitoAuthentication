/**
 * 
 */
package com.huaylupo.cognito.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.huaylupo.cognito.util.CorsHelper;

/**
 * CorsFilter.java class that contains the logic of the CORS Filter.
 * @author ihuaylupo
 * @version 
 * @since 2018-09-10 
 */
@Component
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {
        
        HttpServletResponse response = CorsHelper.addResponseHeaders(res);

        chain.doFilter(req, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
