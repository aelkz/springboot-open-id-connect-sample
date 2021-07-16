package com.redhat.sso.sample.security.config;

import com.redhat.sso.sample.security.jwt.JwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "rest.security", value = "enabled", havingValue = "true")
@Import({SecurityCorsConfig.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SecurityCorsConfig securityCorsConfig;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    public SecurityConfig(SecurityCorsConfig securityCorsConfig) {
        this.securityCorsConfig = securityCorsConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().authorities("ROLE_ANONYMOUS");
        http.cors().configurationSource(corsConfigurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        http.csrf().disable(); // not for production environments!

        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtConverter(clientId))
            .and()
            .and()
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/health").permitAll()
            .anyRequest().authenticated();

    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtConverter(String clientId) {
        return new JwtAuthenticationConverter(clientId);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        if (null != securityCorsConfig.getCorsConfiguration()) {
            source.registerCorsConfiguration("/**", securityCorsConfig.getCorsConfiguration());
        }
        return source;
    }

}
