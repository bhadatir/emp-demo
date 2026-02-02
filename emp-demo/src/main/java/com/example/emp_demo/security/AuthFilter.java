package com.example.emp_demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final String DUMMY_TOKEN = "secret_dummy_token_123";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/auth/login") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/error")) {

            filterChain.doFilter(request, response);
            return;
        }

        boolean isAuthenticated = false;
        if (request.getCookies() != null) {
            isAuthenticated = Arrays.stream(request.getCookies())
                    .anyMatch(c -> "AUTH-TOKEN".equals(c.getName()) && DUMMY_TOKEN.equals(c.getValue()));
        }

        if (isAuthenticated) {
            filterChain.doFilter(request, response);
        } else {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing or Invalid Cookie Token");
            response.getWriter().flush();
        }
    }

    // Optional: Use this to strictly prevent the filter from applying to specific paths at the framework level
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/login") || path.startsWith("/swagger") || path.startsWith("/v3/api-docs");
    }
}
