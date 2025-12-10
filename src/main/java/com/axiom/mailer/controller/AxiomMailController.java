package com.axiom.mailer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.axiom.mailer.service.BulkMailService;

@RestController
@RequestMapping("/api/mail")
public class AxiomMailController {
	@Lazy
	@Autowired
    private BulkMailService mailService;
    @GetMapping("/send-all")
    public ResponseEntity<String> sendAllMails() throws InterruptedException {
        int sentCount = mailService.sendAllCompanyMails();
        if (sentCount == 0) {
            return ResponseEntity.ok("No companies/emails found in DB!");
        }
        return ResponseEntity.ok("Mails sent to " + sentCount + " recipients successfully.");
    }
}