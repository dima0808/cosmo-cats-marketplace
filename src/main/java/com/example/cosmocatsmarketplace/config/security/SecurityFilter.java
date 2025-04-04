package com.example.cosmocatsmarketplace.config.security;

import static com.example.cosmocatsmarketplace.util.SecurityUtils.API_KEY_HEADER;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.HeaderBearerTokenResolver;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

public class SecurityFilter extends OncePerRequestFilter {

  private final BearerTokenResolver tokenResolver = new HeaderBearerTokenResolver(API_KEY_HEADER);

  private final BearerTokenAuthenticationEntryPoint authenticationEntryPoint = new BearerTokenAuthenticationEntryPoint();

  private final AuthenticationFailureHandler authenticationFailureHandler = new AuthenticationEntryPointFailureHandler(
      authenticationEntryPoint);
  private final AuthenticationProvider jwtAuthenticationProvider;

  public SecurityFilter(JwtDecoder jwtDecoder) {
    this.jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    String token;
    try {
      token = this.tokenResolver.resolve(request);
    } catch (OAuth2AuthenticationException invalid) {
      this.logger.trace("Failed to resolve bearer token", invalid);
      this.authenticationEntryPoint.commence(request, response, invalid);
      return;
    }
    if (token == null) {
      this.logger.trace("No token");
      this.authenticationEntryPoint.commence(request, response, null);
      return;
    }

    try {
      BearerTokenAuthenticationToken authenticationRequest = new BearerTokenAuthenticationToken(
          token);
      Authentication authenticate = jwtAuthenticationProvider.authenticate(authenticationRequest);
      if (!authenticate.isAuthenticated()) {
        this.logger.trace("Failed to authenticate authentication request: " + authenticate);
        this.authenticationFailureHandler.onAuthenticationFailure(request, response,
            new OAuth2AuthenticationException("Unauthenticated"));
      } else {
        this.logger.trace("Successfully authenticated authentication request: " + authenticate);
        filterChain.doFilter(request, response);
      }
    } catch (AuthenticationException failed) {
      this.logger.trace("Failed to process authentication request", failed);
      this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
  }
  }
