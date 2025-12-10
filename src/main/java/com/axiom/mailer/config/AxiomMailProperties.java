package com.axiom.mailer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "email")
public class AxiomMailProperties {
	private String fromEmail;
	private String fromName;
	private String subject;	
	private String attachmentsFolder;
	private String templateFile;

	// Getters and Setters
	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getAttachmentsFolder() {
		return attachmentsFolder;
	}

	public void setAttachmentsFolder(String attachmentsFolder) {
		this.attachmentsFolder = attachmentsFolder;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

}
