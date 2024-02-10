package com.webdev.Utilisateur.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.webdev.Utilisateur.service.UtilisateurService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {

  private UtilisateurService utilisateurService;
  private JwtService jwtService;
  

  public JwtFilter(UtilisateurService utilisateurService, JwtService jwtService) {
    this.utilisateurService = utilisateurService;
    this.jwtService = jwtService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

        String token= null;
        String username= null;
        boolean isTokenExpired = true;
        
        // recuperation de Header 
        final String autorization= request.getHeader("Authorization");

        // token: Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImFsYWluQGdtYWlsLmNvbSIsIm5vbSI6IkFsYWluIE1hbmRhZ28ifQ.A4b5H_goTY2Km9gYnc-xeRHxb6Zyw_doW6zzKB0ILgI
        if(autorization!=null && autorization.startsWith("Bearer ")){
          token= autorization.substring(7);
          //utilisateurService.loadUserByUsername(username);
          isTokenExpired =jwtService.isTokentExpired(token);
         username=  jwtService.extractUsername(token);

        }

        if(!isTokenExpired && username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails userdetails= utilisateurService.loadUserByUsername(username);
           UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userdetails,null, userdetails.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
        


    
  }
  
}
