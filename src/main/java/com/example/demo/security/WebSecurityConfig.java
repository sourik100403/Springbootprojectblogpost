package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.config.Customizer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled =true,securedEnabled = true)
public class WebSecurityConfig {
    private static final String[] WHITELIST={
        "/",
        "/login",
        "/register",
        "/db-console/**",
        "/css/**",
        "/fonts/**",
        "/images/**",
        "/js/**"
    };

     @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(WHITELIST)
            .permitAll()
            .requestMatchers("/profile/**").authenticated()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/editor/**").hasAnyRole("ADMIN","EDITOR")
            .requestMatchers("/test").hasAuthority("ACCESS_ADMIN_PANEL")
            .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
            .key("your-very-secure-secret-key")
            .tokenValiditySeconds(7 * 24 * 60 * 60)
            .rememberMeParameter("remember-me")
        )
        .exceptionHandling(exception -> exception
        .accessDeniedPage("/access-denied")
    )
            .logout(logout -> logout
             .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
             .logoutSuccessUrl("/")
             .permitAll()
            // .logoutUrl("/logout")
            //     .logoutSuccessUrl("/")
            //     .permitAll()
            )
            .httpBasic(Customizer.withDefaults()) // 👈 Enables HTTP Basic


            //Todo removed theaseafter upgradingthe db from h2 toinfile
            // http.csrf().disable();
            // http.headers().frameOptions().disable();

            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/db-console/**") // Just for H2 Console
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable()) // Just for H2 Console
            );
        return  http.build();

    }
}
