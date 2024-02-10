package com.webdev.Utilisateur.security;

import java.util.Date;
import java.util.Map;

import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;

import com.webdev.Utilisateur.entity.Utilisateur;
import com.webdev.Utilisateur.service.UtilisateurService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import java.security.Key;

import lombok.AllArgsConstructor;

import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.SignatureAlgorithm;


@AllArgsConstructor
@Service
public class JwtService {
  
  private final String ENCRIPTION_KEY ="bafb6cb0563f4948dad609453f66a0eee40c7f684bda86603b113ad9a7470313";
  private UtilisateurService utilisateurService;
  public Map<String, String> generate(String username){
     Utilisateur utilisateur= this.utilisateurService.loadUserByUsername(username);
    return this.generateJwt(utilisateur);

  }

  private Map <String, String> generateJwt(Utilisateur utilisateur){
    
    final long currentTime= System.currentTimeMillis();
    final long expirationTime = currentTime+30*60*1000;

   final Map<String, Object> claims= Map.of(
      "nom", utilisateur.getNom(),      
      Claims.EXPIRATION, new Date(expirationTime),
      Claims.SUBJECT,  utilisateur.getEmail()
      );
    
    
    final String bearer= Jwts.builder()
                  .setIssuedAt(new Date(currentTime))
                  .setExpiration(new Date(expirationTime))
                  .setSubject(utilisateur.getEmail())
                  .setClaims(claims)
                  .signWith(getKey(),SignatureAlgorithm.HS256) 
                  .compact();
    return Map.of("bearer", bearer);
  }

  private Key getKey(){

   final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
    return Keys.hmacShaKeyFor(decoder);

  }

  public String extractUsername(String token) {
    
    return this.getClaim(token, Claims::getSubject);
  }

  public boolean isTokentExpired(String token) {
    Date expirationDate= this.getClaim(token, Claims::getExpiration); //getExpirationDateFromToken(token);
    return expirationDate.before(new Date());
  }
 /* 
  private Date getExpirationDateFromToken(String token) {
    return this.getClaim(token, Claims::getExpiration);
  }
  */

  private <T> T getClaim (String token, Function<Claims, T> function ){
    Claims claims = getAllClaims(token);

    return function.apply(claims);
  }

  private Claims getAllClaims(String token) {
    
    return Jwts.parserBuilder()
           .setSigningKey(this.getKey())
           .build()
           .parseClaimsJws(token)
           .getBody();
  }
}
