package com.axiom.mailer.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(
    name = "companies",
    indexes = { @Index(columnList = "domain", name = "idx_domain", unique = true) }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "emails")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String domain;

    private String sourceUrl;

    private String description;
	@Column(length = 2000)
    private String personalization;

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<CompanyEmail> emails = new HashSet<>();

    // helper method
    public void addEmail(CompanyEmail companyEmail) {
        companyEmail.setCompany(this);
        this.emails.add(companyEmail);
    }
}
