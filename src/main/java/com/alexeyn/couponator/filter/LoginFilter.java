package com.alexeyn.couponator.filter;

import com.alexeyn.couponator.cache.ICacheController;
import com.alexeyn.couponator.consts.Constants;
import com.alexeyn.couponator.data.LoggedInUserData;
import com.alexeyn.couponator.data.LoginResponseDataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class LoginFilter implements Filter {

    @Autowired
    private ICacheController cacheController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals("OPTIONS")) {
            chain.doFilter(httpRequest, response);
            return;
        }

        String url = httpRequest.getRequestURL().toString();

        if (url.endsWith("/register")) {
            chain.doFilter(httpRequest, response);
            return;
        }

        if (url.endsWith("/login")) {
            chain.doFilter(httpRequest, response);
            return;
        }

        //String token = request.getParameter("token");
        String token = httpRequest.getHeader("Authorization");

        LoggedInUserData loggedInUserData = (LoggedInUserData) cacheController.get(token);
        if (loggedInUserData != null) {
            //			User not loggedIn
            //			Move forward to the next filter in chain
            // TAASE KAVUA
            request.setAttribute(Constants.USER_DATA_KEY, loggedInUserData);
            chain.doFilter(request, response);
            return;
        }

        //		token is not in cache or token is null
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        int unAuthorizedError = 401;
        httpResponse.setStatus(unAuthorizedError);
    }

    @Override
    public void destroy() {

    }
}
