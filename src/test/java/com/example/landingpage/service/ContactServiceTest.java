package com.example.landingpage.service;

import com.example.landingpage.model.ContactForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ContactService.
 */
@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ContactService contactService;

    private ContactForm contactForm;

    @BeforeEach
    void setUp() {
        contactForm = new ContactForm();
        contactForm.setFullName("John Doe");
        contactForm.setEmail("john@example.com");
        contactForm.setPhoneNumber("+1234567890");
        contactForm.setSubject("Test Subject");
        contactForm.setMessage("This is a test message.");
    }

    @Test
    void testProcessContactFormSuccess() {
        doNothing().when(emailService).sendContactFormNotification(any(ContactForm.class));

        boolean result = contactService.processContactForm(contactForm);

        assertTrue(result);
        verify(emailService, times(1)).sendContactFormNotification(any(ContactForm.class));
    }

    @Test
    void testProcessContactFormSanitizesInput() {
        contactForm.setFullName("<script>alert('xss')</script>John Doe");
        contactForm.setMessage("<b>Bold message</b>");

        boolean result = contactService.processContactForm(contactForm);

        assertTrue(result);
        assertFalse(contactForm.getFullName().contains("<script>"));
        assertFalse(contactForm.getMessage().contains("<b>"));
    }

    @Test
    void testProcessContactFormHandlesNullPhoneNumber() {
        contactForm.setPhoneNumber(null);
        doNothing().when(emailService).sendContactFormNotification(any(ContactForm.class));

        boolean result = contactService.processContactForm(contactForm);

        assertTrue(result);
        assertNull(contactForm.getPhoneNumber());
    }

    @Test
    void testProcessContactFormWithException() {
        doThrow(new RuntimeException("Email service error"))
                .when(emailService).sendContactFormNotification(any(ContactForm.class));

        boolean result = contactService.processContactForm(contactForm);

        assertFalse(result);
    }
}
