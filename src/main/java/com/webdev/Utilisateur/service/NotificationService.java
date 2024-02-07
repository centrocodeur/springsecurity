package com.webdev.Utilisateur.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.webdev.Utilisateur.entity.Validation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NotificationService {

  JavaMailSender javaMailSender;

  public void envoyer(Validation validation){
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom("marientaps@gmail.com");
   message.setTo(validation.getUtilisateur().getEmail());
    message.setSubject("Votre code d'activation");
   String texte= String.format("Bonjour %s, <br/>  Votre code d'activation est %s, A bientôt",
     validation.getUtilisateur().getNom(),
     validation.getCode());

    message.setText(texte);

    javaMailSender.send(message);
  
}
}