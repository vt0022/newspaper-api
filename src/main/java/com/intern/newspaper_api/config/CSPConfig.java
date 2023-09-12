package com.intern.newspaper_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Configuration
public class CSPConfig {

    @Bean
    public OncePerRequestFilter addCSPHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

                // Set the CSP header
                // Allow font from Google, image from the same origin
                responseWrapper.setHeader("Content-Security-Policy", "default-src 'none'; font-src 'self' https://fonts.gstatic.com; img-src 'self'");

                filterChain.doFilter(request, responseWrapper);
                responseWrapper.copyBodyToResponse();
            }
        };
    }
}
