package com.axiom.mailer.service;

import com.axiom.mailer.model.CompanyResponse;
import com.axiom.mailer.repo.CompanyRepository;
import com.axiom.mailer.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepo;

    public CompanyService(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    public List<CompanyResponse> loadAllCompanies() {
        return companyRepo.findAll()
                .stream()
                .map(CompanyMapper::toResponse)
                .toList();
    }

    public CompanyResponse loadByDomain(String domain) {
        return CompanyMapper.toResponse(companyRepo.findByDomain(domain));
    }
}
