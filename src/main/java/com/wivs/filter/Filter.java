package com.wivs.filter;

import com.wivs.spring.SpringContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ClassPathXmlApplicationContext context = SpringContext.context;
        AccessControl accessControl = (AccessControl) context.getBean("AccessControl");
        response.setHeader("Access-Control-Allow-Origin", accessControl.allow_Origin);
        response.setHeader("Access-Control-Allow-Methods", accessControl.allow_Methods);
        response.setHeader("Access-Control-Max-Age", accessControl.max_Age);
        response.setHeader("Access-Control-Allow-Headers", accessControl.allow_Headers);
        response.setHeader("Access-Control-Allow-Credentials", accessControl.allow_Credentials);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
