package com.axiom.mailer.mapper;

import com.axiom.mailer.db.model.Company;
import com.axiom.mailer.db.model.CompanyEmail;
import com.axiom.mailer.model.AxiomMailResponse;
import com.axiom.mailer.model.CompanyResponse;

import java.util.List;

public class CompanyMapper {

    public static CompanyResponse toResponse(Company c) {
        List<AxiomMailResponse> emailList = c.getEmails().stream()
                .map(CompanyMapper::toAxiomMailResponse)
                .toList();

        return new CompanyResponse(
                c.getId(),
                c.getName(),
                c.getDomain(),
                c.getSourceUrl(),
                c.getDescription(),
                emailList
        );
    }

    private static AxiomMailResponse toAxiomMailResponse(CompanyEmail e) {
        return new AxiomMailResponse(
                e.getId(),
                e.getEmail(),
                e.getTag()
        );
    }
}
