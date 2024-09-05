package com.ms.upmails.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.upmails.dto.MailDto;
import com.ms.upmails.model.Mail;
import com.ms.upmails.service.MailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/sending")
    public ResponseEntity<Mail> sendingEmail(@RequestBody @Valid MailDto mailDto) {
        Mail mail = new Mail();
        BeanUtils.copyProperties(mailDto, mail);
        mailService.sendMail(mail);
        return new ResponseEntity<>(mail, HttpStatus.CREATED);
    }

    // end-point for checking api status
    @GetMapping("/ping")
    public ResponseEntity<String> pong(){
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
