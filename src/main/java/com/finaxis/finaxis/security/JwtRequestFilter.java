package com.finaxis.finaxis.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.repository.UserRepository;
import com.finaxis.finaxis.security.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("Request URI: " + requestURI);

        // Skip the filter for /auth/** endpoints
        if (requestURI.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
                if (user.isPresent()) {
                    User localUser = user.get();
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(localUser, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException exception) {
                System.out.println("JWTDecodeException: " + exception.getMessage());
            }
        } else {
            System.out.println("Authorization header missing or does not start with Bearer");
        }
        filterChain.doFilter(request, response);
    }
}
