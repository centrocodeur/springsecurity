package com.webdev.Utilisateur.repository;

import org.springframework.data.repository.CrudRepository;

import com.webdev.Utilisateur.entity.Validation;

import java.util.Optional;


public interface ValidationRepository extends CrudRepository<Validation,Integer> {

    
    Optional <Validation> findByCode(String code);
}
