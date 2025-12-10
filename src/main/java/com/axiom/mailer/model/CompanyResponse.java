package com.axiom.mailer.model;

import java.util.List;

public record CompanyResponse( Long id,
        String name,
        String domain,
        String sourceUrl,
        String description,
        List<AxiomMailResponse> emails) {

}
