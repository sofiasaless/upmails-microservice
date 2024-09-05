package com.ms.upmails.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.upmails.model.Mail;

public interface MailRepository extends JpaRepository<Mail, UUID> {
    
}
