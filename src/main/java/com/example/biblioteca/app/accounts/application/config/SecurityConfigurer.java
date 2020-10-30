package com.example.biblioteca.app.accounts.application.config;

import com.example.biblioteca.app.accounts.application.filters.AuthenticationFilter;
import com.example.biblioteca.app.accounts.application.filters.TokenValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final TokenValidationFilter tokenValidationFilter;
    private final AuthenticationFilter authenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenValidationFilter, AuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v1/movie/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/v1/movie/**").hasAuthority("ADMIN")
                .antMatchers("/v1/login", "/v1/register").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
