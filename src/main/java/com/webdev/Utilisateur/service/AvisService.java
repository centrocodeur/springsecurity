package com.webdev.Utilisateur.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.webdev.Utilisateur.entity.Avis;
import com.webdev.Utilisateur.entity.Utilisateur;
import com.webdev.Utilisateur.repository.AvisRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AvisService {
  

  private final AvisRepository avisRepository;

  public void creer(Avis avis){
    Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       avis.setUtilisateur(utilisateur);
       this.avisRepository.save(avis);
    

  }

}
