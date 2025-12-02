package com.example.landingpage.service;

import com.example.landingpage.model.ContactForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Email notification service for sending contact form submissions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.contact.recipient-email:admin@example.com}")
    private String recipientEmail;

    @Value("${app.contact.from-email:noreply@example.com}")
    private String fromEmail;

    /**
     * Sends contact form notification email asynchronously.
     * 
     * @param contactForm the contact form data
     */
    @Async
    public void sendContactFormNotification(ContactForm contactForm) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(recipientEmail);
            message.setSubject("New Contact Form Submission: " + contactForm.getSubject());
            message.setText(formatEmailBody(contactForm));

            mailSender.send(message);
            log.info("Contact form notification sent successfully for: {}", contactForm.getEmail());
        } catch (Exception e) {
            log.error("Failed to send contact form notification for: {}", contactForm.getEmail(), e);
            // Even if email fails, we don't throw exception to provide better UX
        }
    }

    /**
     * Formats the email body from contact form data.
     */
    private String formatEmailBody(ContactForm contactForm) {
        return String.format(
            """
            New Contact Form Submission
            
            From: %s
            Email: %s
            Phone: %s
            Subject: %s
            
            Message:
            %s
            """,
            contactForm.getFullName(),
            contactForm.getEmail(),
            contactForm.getPhoneNumber() != null ? contactForm.getPhoneNumber() : "Not provided",
            contactForm.getSubject(),
            contactForm.getMessage()
        );
    }
}
