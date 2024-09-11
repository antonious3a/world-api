package dev.antonio3a.worldapi.infra.configurations;

import dev.antonio3a.worldapi.infra.util.MyJwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final MyJwtAuthenticationConverter myJwtAuthenticationConverter;

    @Bean
    @ConfigurationProperties(prefix = "env.keycloak.policy-enforcer")
    public PolicyEnforcerConfig policyEnforcerConfig() {
        return new PolicyEnforcerConfig();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(
                                (request, response, authException) ->
                                        response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage())
                        ).accessDeniedHandler(
                                (request, response, accessDeniedException) ->
                                        response.sendError(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage())
                        )
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/world/api/v1/docs/**", "/").permitAll()
                                .requestMatchers("/actuator/info", "/actuator/health", "/actuator/prometheus").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(myJwtAuthenticationConverter)
                        )
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAfter(new ServletPolicyEnforcerFilter(httpRequest -> policyEnforcerConfig()), BearerTokenAuthenticationFilter.class)
                .build();
    }
}