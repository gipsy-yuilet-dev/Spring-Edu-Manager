package com.springedumanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
/**
 * Configuracion central de seguridad para vistas web y API REST.
 */
public class SecurityConfig {

    @Value("${app.security.admin.username}")
    private String adminUsername;

    @Value("${app.security.admin.password}")
    private String adminPassword;

    @Value("${app.security.user.username}")
    private String userUsername;

    @Value("${app.security.user.password}")
    private String userPassword;

    @Bean
    PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    var admin = User.withUsername(adminUsername)
        .password(passwordEncoder.encode(adminPassword))
        .roles("ADMIN")
        .build();

    var user = User.withUsername(userUsername)
        .password(passwordEncoder.encode(userPassword))
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cursos/nuevo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cursos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/estudiantes/nuevo").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/estudiantes").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/estudiantes/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                    .requestMatchers("/cursos/**", "/estudiantes/**", "/interoperabilidad", "/").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/h2-console/**"))
                .httpBasic(basic -> {
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/estudiantes", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/acceso-denegado"));

        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
