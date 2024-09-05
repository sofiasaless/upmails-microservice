package com.ms.upmails.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ms.upmails.enums.StatusEmail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TB_EMAIL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID emailId;
    private String ownerRef;
    private String emailFrom;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;
    
}
