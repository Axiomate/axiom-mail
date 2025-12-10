package com.axiom.mailer.db.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "company_emails",
    uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "company")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CompanyEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String email;

    private String tag; // hr, careers, jobs, generic

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
