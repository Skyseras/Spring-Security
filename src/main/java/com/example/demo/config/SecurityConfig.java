package com.example.demo.config;

import com.example.demo.model.RoleType;
import com.example.demo.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final Filter jwtAuthFilter;
    private final AppUserService appUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/**/auth/**")
                .permitAll()
                .antMatchers("/api/v1/products/").hasAnyAuthority(RoleType.ROLE_ADMIN.name(), RoleType.ROLE_SUPPLIER.name(), RoleType.ROLE_USER.name())
                .antMatchers("/api/v1/products/add").hasAnyAuthority(RoleType.ROLE_ADMIN.name(), RoleType.ROLE_SUPPLIER.name())
                .antMatchers("/api/v1/products/**").hasAnyAuthority(RoleType.ROLE_ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);;
        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
/*
        return NoOpPasswordEncoder.getInstance();
*/
        return new BCryptPasswordEncoder(10);    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

                return appUserService.findByEmail(email);
        }

    };
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
