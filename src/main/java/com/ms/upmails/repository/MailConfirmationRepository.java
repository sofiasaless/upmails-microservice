package com.ms.upmails.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.upmails.model.MailConfirmation;

public interface MailConfirmationRepository extends JpaRepository<MailConfirmation, UUID> {
    
}