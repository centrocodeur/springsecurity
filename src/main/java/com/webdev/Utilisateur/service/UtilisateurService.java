package com.webdev.Utilisateur.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webdev.Utilisateur.entity.Role;
import com.webdev.Utilisateur.entity.Utilisateur;
import com.webdev.Utilisateur.entity.Validation;
import com.webdev.Utilisateur.repository.UtilisateurRepository;
import com.webdev.Utilisateur.role.TypeDeRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UtilisateurService {

  private UtilisateurRepository utilisateurRepository;
  private BCryptPasswordEncoder passwordEncoder;
  private ValidationService validationService;


  public void inscription(Utilisateur utilisateur){
    // Verification de mail

    if(!utilisateur.getEmail().contains("@")){
      throw new RuntimeException("Votre mail est invalide!");
    }
     
    if(!utilisateur.getEmail().contains(".")){
      throw new RuntimeException("Votre mail est invalide!");
    }
     
    Optional<Utilisateur> utilisateurOptional= this.utilisateurRepository.findByEmail(utilisateur.getEmail());
    
    if(utilisateurOptional.isPresent()){
      throw new RuntimeException("Votre mail est déjà utilisé!");
    }
    // encodage de mot de passe
      String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
      utilisateur.setMdp(mdpCrypte);

      Role roleUtilisateur =new Role();
      roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
      utilisateur.setRole(roleUtilisateur);
      
     utilisateur= this.utilisateurRepository.save(utilisateur);

     this.validationService.enregistrer(utilisateur);
  }


  public void activation(Map<String, String> activation) {
      Validation validation= this.validationService.lireEnFonctionDuCode(activation.get("code"));

      if(Instant.now().isAfter(validation.getExpiration())){
        throw new RuntimeException("Votre code a expiré!");
      }
     Utilisateur utilisateurActiver= this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(()-> 
         new RuntimeException("Utilisateur inconnu "));
     utilisateurActiver.setActif(true);
     this.utilisateurRepository.save(utilisateurActiver);

  }


  

}
