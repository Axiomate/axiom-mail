package com.axiom.mailer.model;

public record AxiomMailRequest(String to,
        String subject,
        String htmlBody,
        String companyName,
        String personalization) {

}
