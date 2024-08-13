package com.finaxis.finaxis.security;

import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.repository.UserRepository;
import com.finaxis.finaxis.security.services.JWTService;
import com.finaxis.finaxis.security.services.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (request.getRequestURI().startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        jwt = authHeader.substring(7);  // Extract the JWT token
        username = jwtService.getUsername(jwt);  // Extract the username from the token

        // Check if the token is blacklisted
        if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Return 401 Unauthorized
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
            if (user.isPresent()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getPhoneNumber());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("user not found");
            }
        }
        filterChain.doFilter(request, response);
    }
}
