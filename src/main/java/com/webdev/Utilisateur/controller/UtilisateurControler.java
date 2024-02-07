package com.webdev.Utilisateur.controller;


import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webdev.Utilisateur.entity.Utilisateur;
import com.webdev.Utilisateur.service.UtilisateurService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurControler {
   
   private UtilisateurService utilisateurService;

  @PostMapping(path = "inscription")
  public void inscription(@RequestBody Utilisateur utilisateur){
   
    log.info("Inscription");
    this.utilisateurService.inscription(utilisateur);
  }

  @PostMapping(path = "activation")
  public void activation(@RequestBody Map<String, String> activation ){
   
    log.info("activation");
    this.utilisateurService.activation(activation);
  }
  
}
