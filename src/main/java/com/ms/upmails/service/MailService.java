package com.ms.upmails.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.upmails.enums.StatusEmail;
import com.ms.upmails.model.Mail;
import com.ms.upmails.model.MailConfirmation;
import com.ms.upmails.repository.MailConfirmationRepository;
import com.ms.upmails.repository.MailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
    
    private final MailRepository mailRepository;

    private final MailConfirmationRepository mailConfirmationRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${api.email.touse}")
    private String workMail;

    // method for reciving emails by "contact us"
    public Mail sendMail(Mail mail) {
        mail.setSendDateEmail(LocalDateTime.now());
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mail.getEmailFrom());
            message.setTo(workMail);
            message.setSubject(mail.getSubject());
            message.setText(mail.getText());

            emailSender.send(message);

            mail.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            mail.setStatusEmail(StatusEmail.ERROR);
        } finally {
            // finally a confirmation email will be send to the person who made contact
            sendConfirmationMail(mail.getOwnerRef(), mail.getEmailFrom());
            return mailRepository.save(mail);
        }
        
    }

    // method for sending confirmation emails after users sends contact email
    public MailConfirmation sendConfirmationMail(String onwerRef, String emailTo) {
        MailConfirmation upBusinessConfirmationMail = MailConfirmation.builder()
            .ownerRef("UpBusiness")
            .subject("Que bom que nos contatou!")
            .emailTo(emailTo)
            .emailFrom(workMail)
            .text("Olá, " + onwerRef + ", recebemos sua mensagem! \nEm breve entraremos em contato para maiores discussões. \nAtenciosamente, Equipe UpBusiness.")
            .sendDateEmail(LocalDateTime.now())
        .build();
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(upBusinessConfirmationMail.getEmailFrom());
            message.setTo(upBusinessConfirmationMail.getEmailTo());
            message.setSubject(upBusinessConfirmationMail.getSubject());
            message.setText(upBusinessConfirmationMail.getText());

            emailSender.send(message);

            upBusinessConfirmationMail.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            upBusinessConfirmationMail.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return mailConfirmationRepository.save(upBusinessConfirmationMail);
        }
    }

}
