package com.alexeyn.couponator.filter;

import com.alexeyn.couponator.cache.ICacheController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Autowired
    private ICacheController cacheController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //Exclude: "Register", "Login"
        String token = request.getParameter("token");
        if (cacheController.get(token) != null) {
            // U're logged in - all is good
            // Move forward to the next filter in chain
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(401);
    }

    @Override
    public void destroy() {

    }
}
