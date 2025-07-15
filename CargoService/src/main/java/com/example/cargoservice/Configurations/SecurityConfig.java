package com.example.cargoservice.Configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${swagger.username:admin}")
    private String swaggerUsername;

    @Value("${swagger.password:admin123}")
    private String swaggerPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username(swaggerUsername)
                .password(passwordEncoder().encode(swaggerPassword))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Swagger UI endpoints'leri için authentication gerekli
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").authenticated()
                        .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml").authenticated()
                        .requestMatchers("/swagger-resources/**").authenticated()
                        .requestMatchers("/webjars/**").authenticated()

                        // Actuator endpoints (health check vs.)
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/actuator/**").authenticated()

                        // Ana API endpoints'leri - bu kısmı ihtiyacınıza göre düzenleyin
                        .requestMatchers("/api/v1/packages/**").permitAll() // API'ler açık
                        .requestMatchers("/api/**").permitAll() // Tüm API'ler açık

                        // Diğer tüm istekler
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic
                        .realmName("Cargo Service API Documentation")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}