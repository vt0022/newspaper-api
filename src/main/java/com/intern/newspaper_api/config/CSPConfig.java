package com.intern.newspaper_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Configuration
public class CSPConfig {
    @Value("${CSP_SCRIPT_URL}")
    private String CSP_SCRIPT_URL;

    @Bean
    public OncePerRequestFilter addCSPHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

                // Set the CSP header
                responseWrapper.setHeader("Content-Security-Policy",
                        "default-src 'none' ; " +
                                "font-src 'self' https://fonts.gstatic.com ; " +
                                "img-src 'self' data: ; " +
                                "script-src 'self' "+  CSP_SCRIPT_URL + " ; " +
                                "style-src-elem 'self' " + CSP_SCRIPT_URL + " ; " +
                                "connect-src 'self' " + CSP_SCRIPT_URL );

                filterChain.doFilter(request, responseWrapper);
                responseWrapper.copyBodyToResponse();
            }
        };
    }
}
