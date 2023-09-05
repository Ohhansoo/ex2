package com.example.club.config;

import lombok.extern.log4j.Log4j2;
import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

   @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        log.info("----------------------filterChain-------------------------");


        http.authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/sample/all").permitAll()
                    .requestMatchers("/sample/member").hasRole("USER");
        }).formLogin(withDefaults());

       http.csrf((csrfConfig) ->
               csrfConfig.disable()
       );
       http.logout((logoutConfig) ->
               log.info("------log out--------")
       );

        http.oauth2Login((oauth2) -> log.info("------OAuth Login--------"));

        return http.build();
    }

/*  @Bean
   public InMemoryUserDetailsManager userDetailsService() {
      UserDetails user = User.builder()
              .username("user1")
              .password(passwordEncoder().encode("1111"))
              .roles("USER")
              .build();
       return new InMemoryUserDetailsManager(user);
    }*/
}
