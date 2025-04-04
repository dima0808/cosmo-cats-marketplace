package com.example.cosmocatsmarketplace.config.security;

import static com.example.cosmocatsmarketplace.util.SecurityUtils.ROLE_CLAIMS_HEADER;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.HeaderBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

  private static final String COSMO_CATS_V1_API = "/api/v1/cosmo-cats/**";
  private static final String ORDERS_V1_API = "/api/v1/orders/**";
  private static final String PRODUCTS_V1_API = "/api/v1/products/**";

  @Bean
  @Order(1)
  public SecurityFilterChain securityFilterChainCosmoCatV1(HttpSecurity http) throws Exception {

    http.securityMatcher(COSMO_CATS_V1_API)
        .cors(Customizer.withDefaults())
        .csrf(CsrfConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> authorize.requestMatchers(COSMO_CATS_V1_API).authenticated())
        .oauth2ResourceServer(
            oAuth2 -> oAuth2.bearerTokenResolver(new HeaderBearerTokenResolver(ROLE_CLAIMS_HEADER))
                .jwt(
                    jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain securityFilterChainOrdersV1(HttpSecurity http, JwtDecoder decoder)
      throws Exception {

    http.securityMatcher(ORDERS_V1_API)
        .cors(withDefaults())
        .csrf(CsrfConfigurer::disable)
        .addFilterBefore(new SecurityFilter(decoder), UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> authorize.requestMatchers(antMatcher(ORDERS_V1_API)).authenticated())
        .oauth2ResourceServer(
            oAuth2 -> oAuth2.bearerTokenResolver(new HeaderBearerTokenResolver(ROLE_CLAIMS_HEADER))
                .jwt(
                    jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  @Bean
  @Order(3)
  public SecurityFilterChain securityFilterChainProductV1(HttpSecurity http) throws Exception {

    http.securityMatcher(PRODUCTS_V1_API)
        .cors(Customizer.withDefaults())
        .csrf(CsrfConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> authorize.requestMatchers(PRODUCTS_V1_API).authenticated())
        .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(
            jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();

  }

  @Bean
  @Order(4)
  public SecurityFilterChain securityFilterChainGithubV1(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/login/**").permitAll()
            .requestMatchers("/api/v1/**").authenticated())
        .oauth2Login(withDefaults());
    return http.build();
  }

  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new AuthorityConverter());
    return jwtAuthenticationConverter;
  }


}