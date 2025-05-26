package com.tu.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // protects endpoints
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/api/orders/**", "/api/coupons/actives", "/api/checkout/**")
                        .authenticated()
                        .requestMatchers("/api/admin/product-categories/**", "/api/admin/coupons/**")
                        .hasAuthority("Admin")
                        .anyRequest().permitAll())
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(
                     jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(customJWTAuthenticationConverter())
                ));

        // CORS filter
        http.cors(Customizer.withDefaults());

        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());
        // friendly response for 401 error
        Okta.configureResourceServer401ResponseBody(http);

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter customJWTAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new JWTGrantedAuthoritiesConverter());

        return converter;
    }
}
