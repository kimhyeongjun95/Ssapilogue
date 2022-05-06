package com.ssafy.ssapilogue.api.config;

import com.ssafy.ssapilogue.api.exception.CustomException;
import com.ssafy.ssapilogue.api.exception.ErrorCode;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.core.domain.User;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken(request);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String userEmail = jwtTokenProvider.getUserEmail(token);
            if (userEmail != null) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "token expired");
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }
}
