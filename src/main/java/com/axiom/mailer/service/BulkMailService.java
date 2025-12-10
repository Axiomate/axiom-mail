package com.axiom.mailer.service;

import com.axiom.mailer.config.AxiomMailProperties;
import com.axiom.mailer.db.model.Company;
import com.axiom.mailer.db.model.CompanyEmail;
import com.axiom.mailer.db.model.EmailLog;
import com.axiom.mailer.repo.CompanyRepository;
import com.axiom.mailer.repo.EmailLogRepository;
import com.axiom.mailer.model.AxiomMailRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BulkMailService {
	@Lazy
	@Autowired
	private CompanyRepository companyRepo;
	@Lazy
	@Autowired
	private final AxiomMailService mailService;
	@Lazy
	@Autowired
	private final AxiomMailProperties emailProps;
	@Lazy
	@Autowired
	private EmailLogRepository emailLogRepository;

	public BulkMailService(CompanyRepository companyRepo, AxiomMailService mailService,
			AxiomMailProperties emailProps) {
		this.companyRepo = companyRepo;
		this.mailService = mailService;
		this.emailProps = emailProps;
	}

	public int sendAllCompanyMails() {

		List<Company> companies = companyRepo.findAll();
		if (companies.isEmpty())
			return 0;

		// Read template from file specified in application.yml
		String templateFileName = emailProps.getTemplateFile();
		String subject = emailProps.getSubject();
		String mailTemplate = readTemplate(templateFileName);

		int sentCount = 0;

		for (Company company : companies) {
			for (CompanyEmail email : company.getEmails()) {
				System.out.println("Sending to: " + email.getEmail());
				AxiomMailRequest request = new AxiomMailRequest(email.getEmail(), subject, mailTemplate,
						company.getName(), company.getPersonalization());
				String result = mailService.sendMail(request);
				try {
			        Thread.sleep(5000);
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
				EmailLog log = EmailLog.builder().companyEmail(email).subject(subject)
						.sent(result.startsWith("Mail sent successfully")).resultMessage(result)
						.sentAt(java.time.LocalDateTime.now()).build();
				emailLogRepository.save(log);
				sentCount++;
			}

		}
		return sentCount;
	}

	private String readTemplate(String fileName) {
		try {
//			ClassPathResource resource = new ClassPathResource("templates/" + fileName);
//			return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
			return Files.readString(Path.of(fileName), StandardCharsets.UTF_8);
//	        return Files.lines(Path.of(fileName), StandardCharsets.UTF_8)
//                    .collect(Collectors.joining(System.lineSeparator()));
		} catch (Exception e) {
			throw new RuntimeException("Failed to read mail template: " + e.getMessage(), e);
		}
	}
}
