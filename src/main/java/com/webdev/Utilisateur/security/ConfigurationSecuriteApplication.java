package com.webdev.Utilisateur.security;

import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigurationSecuriteApplication {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

    return
    httpSecurity
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(
                   authorize ->
                   
                   authorize
                     .requestMatchers("/inscription").permitAll()
                     .requestMatchers("/activation").permitAll()
                     .anyRequest().authenticated()
                   ).build();
                
              
  }
  @Bean
  public BCryptPasswordEncoder passwordEncoder (){
    return new BCryptPasswordEncoder();
  }

  
}
