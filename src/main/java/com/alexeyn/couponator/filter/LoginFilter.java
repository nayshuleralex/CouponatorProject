package com.alexeyn.couponator.filter;

import com.alexeyn.couponator.cache.ICacheController;
import com.alexeyn.couponator.data.LoginResponseDataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Autowired
    private ICacheController cacheController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getRequestURL().toString().endsWith("/login")) {
            // A Login request is unnecessary
            chain.doFilter(request, response);
            return;
        }

        String strToken = req.getParameter("token");
        HttpServletResponse resp = (HttpServletResponse) response;
        if (strToken == null) {
            // 401 - Unauthorized in http
            resp.setStatus(401);
            return;
        }
        int token = Integer.parseInt(strToken);

        if (cacheController.get(token) == null) {
            // 401 - Unauthorized in http
            resp.setStatus(401);
            return;
        }
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
