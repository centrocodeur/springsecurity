package com.webdev.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.webdev.Utilisateur.entity.Avis;

public interface AvisRepository extends JpaRepository<Avis, Integer> {
  
}
