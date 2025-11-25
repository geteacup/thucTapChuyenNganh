package com.example.IdentityService.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    private final String[] PUBLIC_POST_ENDPOINTS = {
            "/auth/login", "/auth/introspect", "/users", "/auth/logout", "/auth/refresh", "/home"
    };
    private final String[] PUBLIC_GET_ENDPOINTS = {
            "/home", "/", "/login", "/home/login",
            "/tonkho", "/images/**", "/scripts/**", "/styles/**", "/css/**",
            "/tonkho/loaisanpham/**", "/tonkho/nhacungcap/**",
            "/nhacungcap", "/loaisanpham", "/users/myInfo",
            "/webjars/**", "/js/**", "/error","/admin","/staff"
    };


//    private final JWTAuthenFilter jwtAuthenFilter;
    private final CustomJwtDecoder jwtDecoder;

    @Autowired
    public SecurityConfig(CustomJwtDecoder jwtDecoder/*, JWTAuthenFilter jwtAuthenFilter*/) {
        this.jwtDecoder = jwtDecoder;
//        this.jwtAuthenFilter = jwtAuthenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/scripts/**"),
                new AntPathRequestMatcher("/images/**"),
                new AntPathRequestMatcher("/webjars/**"),
                new AntPathRequestMatcher("/js/**")
        );
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                );
        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
        );
        http.logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
        );

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder)
                        .jwtAuthenticationConverter(jwtConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        return http.build();
    }

    @Bean
    JwtAuthenticationConverter jwtConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return converter;
    }
}
