package com.axiom.mailer.db.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to CompanyEmail entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_email_id", nullable = false)
    private CompanyEmail companyEmail;

    private String subject;

    private Boolean sent; // true if email was successfully sent
    @Lob
    @Column(name = "result_message")
    private String resultMessage; // e.g., "Mail sent successfully" or error

    private LocalDateTime sentAt; // timestamp of sending
}
