package com.example.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("---------------Security Filter Chain--------------------");

        http
                .authorizeHttpRequests(
                        config -> config
                                .requestMatchers("/css/**", "/js/**", "/images/**" ).permitAll()
                                .requestMatchers("/", "/members/**", "/item/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );

        http
                .csrf(csrf-> csrf.disable())

                .formLogin(
                        form->form.loginPage("/members/login")
                                .defaultSuccessUrl("/")

                                //login화면에서 name=username이면 생략가능
                                //username -> email쓰기 때문에 반드시 기입해야함
                                .usernameParameter("email")
//                                .passwordParameter("password")
                                .failureUrl("/members/login/error")

                )
                .logout(logout->
                        logout.logoutSuccessUrl("/members/logout")
                        .logoutSuccessUrl("/")
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
