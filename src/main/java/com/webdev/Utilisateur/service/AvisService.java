package com.webdev.Utilisateur.service;

import org.springframework.stereotype.Service;

import com.webdev.Utilisateur.entity.Avis;
import com.webdev.Utilisateur.repository.AvisRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AvisService {
  

  private final AvisRepository avisRepository;

  public void creer(Avis avis){
    this.avisRepository.save(avis);

  }

}
