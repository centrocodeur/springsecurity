package com.webdev.Utilisateur.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.webdev.Utilisateur.entity.Utilisateur;

public interface UtilisateurRepository extends CrudRepository <Utilisateur, Integer>{

 Optional<Utilisateur> findByEmail(String email);

  

  
}
