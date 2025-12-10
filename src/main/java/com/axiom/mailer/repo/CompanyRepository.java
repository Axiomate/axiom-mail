package com.axiom.mailer.repo;

import com.axiom.mailer.db.model.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    // Load companies WITH emails in ONE query
    @EntityGraph(attributePaths = "emails")
    List<Company> findAll();

    @EntityGraph(attributePaths = "emails")
    Company findByDomain(String domain);
}