package jp.co.axa.apidemo.configs;

import jp.co.axa.apidemo.filters.ApiKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  /** API Key authentication filter */
  @Autowired private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.addFilterBefore(apiKeyAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .csrf()
        .disable()
        .logout()
        .disable()
        .httpBasic()
        .disable()
        .formLogin()
        .disable();
    return http.build();
  }
}
