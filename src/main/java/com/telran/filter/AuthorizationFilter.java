package com.telran.filter;

import com.telran.entity.UserSession;
import com.telran.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//javax.persistence.*
import java.io.IOException;

@Component
public class AuthorizationFilter implements Filter {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        System.out.println("AuthorizationFilter -> doFilter");
        System.out.println("AuthorizationFilter -> request URI: " + request.getRequestURI());
//        System.out.println("AuthorizationFilter -> Authorization header: " + request.getHeader("Authorization"));
        System.out.println("AuthorizationFilter -> TelRan header: " + request.getHeader("TelRan"));
        System.out.println();
        if (request.getRequestURI().equals("/register") || request.getRequestURI().equals("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = request.getHeader("TelRan");
        if (token == null) {
            response.sendError(401);
            return;
        }

        UserSession userSession = userSessionRepository.findByTokenAndIsValidTrue(token);
        if (userSession == null) {
            response.sendError(401);
            return;
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }
}
