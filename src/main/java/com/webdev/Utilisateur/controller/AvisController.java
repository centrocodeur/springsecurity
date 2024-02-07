package com.webdev.Utilisateur.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webdev.Utilisateur.entity.Avis;
import com.webdev.Utilisateur.service.AvisService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("avis")
@RestController
public class AvisController {
  private final AvisService avisService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
    public void crear(@RequestBody Avis avis){
    this.avisService.creer(avis);
  }
  
}
