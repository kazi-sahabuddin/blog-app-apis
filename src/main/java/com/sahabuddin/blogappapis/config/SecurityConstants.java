package com.sahabuddin.blogappapis.config;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
    };

}
