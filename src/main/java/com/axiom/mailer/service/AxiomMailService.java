package com.axiom.mailer.service;

import java.io.File;

import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.axiom.mailer.config.AxiomMailProperties;
import com.axiom.mailer.constants.AxiomMailConstants;
import com.axiom.mailer.model.AxiomMailRequest;

import jakarta.mail.internet.MimeMessage;

@Service
public class AxiomMailService {

	private final JavaMailSender mailSender;
    private final AxiomMailProperties props;

    public AxiomMailService(@Lazy JavaMailSender mailSender, @Lazy AxiomMailProperties props) {
        this.mailSender = mailSender;
        this.props = props;
    }

    public String sendMail(AxiomMailRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String fromFullName = String.format("%s <%s>", props.getFromName(), props.getFromEmail());

            helper.setFrom(fromFullName);
            helper.setTo(request.to());
            helper.setSubject(replacePlaceholder(AxiomMailConstants.COMPANY_PLACEHOLDER,request.subject(), request.companyName()) );

            // Replace placeholder here
            String finalHtml = replacePlaceholder(AxiomMailConstants.COMPANY_PLACEHOLDER,request.htmlBody(), request.companyName());
            finalHtml = replacePlaceholder(AxiomMailConstants.PERSONALIZATION_PLACEHOLDER,finalHtml, request.personalization());
            finalHtml = finalHtml
                    .replace("\r\n", "<br>")
                    .replace("\n", "<br>");
            helper.setText(finalHtml, true);
            File folder = new File(props.getAttachmentsFolder());
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        helper.addAttachment(file.getName(), file);
                    }
                }
            }

            mailSender.send(mimeMessage);
            return "Mail sent successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: " + e.getMessage();
        }
    }
    
    private String replacePlaceholder(String placeHolder, String htmlBody, String companyName) {
        return htmlBody.replace(placeHolder, companyName);
    }


}
