package com.webdev.Utilisateur.service;

import java.time.Instant;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.webdev.Utilisateur.entity.Utilisateur;
import com.webdev.Utilisateur.entity.Validation;
import com.webdev.Utilisateur.repository.ValidationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ValidationService {
  
  private ValidationRepository validationRepository;
  private NotificationService notificationService;

  public void enregistrer(Utilisateur utilisateur){
    Validation validation= new Validation();
    validation.setUtilisateur(utilisateur);
    Instant creation = Instant.now();
    Instant expiration= creation.plusSeconds(600);
    
    validation.setExpiration(expiration);

    Random random =new Random();
    int randomInteger= random.nextInt(999999);

    String  code = String.format("%06d", randomInteger);

    validation.setCode(code);
    this.validationRepository.save(validation);
    this.notificationService.envoyer(validation);
  }
   
  public Validation lireEnFonctionDuCode(String code ){
     
    return this.validationRepository.findByCode(code).orElseThrow(()-> new RuntimeException("votre code est invalide"));
    
  }
}
