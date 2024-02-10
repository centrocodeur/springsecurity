package com.webdev.Utilisateur.security;

import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.webdev.Utilisateur.service.UtilisateurService;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class ConfigurationSecuriteApplication {

 // private final UserDetailsService userDetailsService;
/* 
  public ConfigurationSecuriteApplication(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
  */
  /* @Autowired
  JwtFilter jwtFilter;
   */
   private final BCryptPasswordEncoder bCryptPasswordEncoder;
   private final JwtFilter jwtFilter;
   private final UserDetailsService userDetailsService;

   

  public ConfigurationSecuriteApplication(JwtFilter jwtFilter, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtFilter = jwtFilter;
    this.userDetailsService = userDetailsService;
  }
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
                     .requestMatchers("/connexion").permitAll()
                     .anyRequest().authenticated()
                   )
                   .sessionManagement(httpSecuritySessionManagementConfigurer ->
                   httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)
                     )
                    .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                   .build();
                
              
  }
  /* 
  @Bean
  public BCryptPasswordEncoder passwordEncoder (){
    return new BCryptPasswordEncoder();
  }
  */

  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

    return authenticationConfiguration.getAuthenticationManager();
  }
/* 
  @Bean
  public UserDetailsService userDetailsService(){
    return new UtilisateurService();
  }
  */

  @Bean
  public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
    return daoAuthenticationProvider;
  }
}
