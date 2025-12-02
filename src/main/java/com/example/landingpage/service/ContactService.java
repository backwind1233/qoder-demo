package com.example.landingpage.service;

import com.example.landingpage.model.ContactForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service for processing contact form submissions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final EmailService emailService;

    /**
     * Processes a contact form submission.
     * 
     * @param contactForm the contact form data
     * @return true if processing was successful
     */
    public boolean processContactForm(ContactForm contactForm) {
        try {
            // Log the submission attempt
            log.info("Processing contact form submission from: {} at {}", 
                contactForm.getEmail(), LocalDateTime.now());

            // Sanitize input data (additional security layer)
            sanitizeContactForm(contactForm);

            // Send email notification asynchronously
            emailService.sendContactFormNotification(contactForm);

            // Log successful submission
            log.info("Contact form submission processed successfully for: {}", contactForm.getEmail());

            return true;
        } catch (Exception e) {
            log.error("Error processing contact form submission from: {}", contactForm.getEmail(), e);
            return false;
        }
    }

    /**
     * Sanitizes contact form data to prevent XSS attacks.
     */
    private void sanitizeContactForm(ContactForm contactForm) {
        // Basic HTML/script tag sanitization
        contactForm.setFullName(sanitizeInput(contactForm.getFullName()));
        contactForm.setEmail(sanitizeInput(contactForm.getEmail()));
        if (contactForm.getPhoneNumber() != null) {
            contactForm.setPhoneNumber(sanitizeInput(contactForm.getPhoneNumber()));
        }
        contactForm.setSubject(sanitizeInput(contactForm.getSubject()));
        contactForm.setMessage(sanitizeInput(contactForm.getMessage()));
    }

    /**
     * Removes potentially dangerous HTML tags and scripts from input.
     */
    private String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Remove HTML tags and script content
        return input.replaceAll("<script[^>]*>.*?</script>", "")
                   .replaceAll("<[^>]+>", "")
                   .trim();
    }
}
