package com.example.landingpage.controller;

import com.example.landingpage.model.ContactForm;
import com.example.landingpage.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for LandingPageController.
 */
@WebMvcTest(LandingPageController.class)
class LandingPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    void testShowLandingPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("contactForm"));
    }

    @Test
    void testSubmitContactFormWithValidData() throws Exception {
        when(contactService.processContactForm(any(ContactForm.class))).thenReturn(true);

        mockMvc.perform(post("/contact")
                        .param("fullName", "John Doe")
                        .param("email", "john@example.com")
                        .param("subject", "Test Subject")
                        .param("message", "This is a test message with enough characters."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/#contact"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void testSubmitContactFormWithInvalidData() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("fullName", "J")  // Too short
                        .param("email", "invalid-email")  // Invalid format
                        .param("subject", "Test")  // Too short
                        .param("message", "Short"))  // Too short
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().hasErrors());
    }

    @Test
    void testSubmitContactFormWithMissingRequiredFields() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("fullName", "")
                        .param("email", "")
                        .param("subject", "")
                        .param("message", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().hasErrors());
    }

    @Test
    void testSubmitContactFormServiceFailure() throws Exception {
        when(contactService.processContactForm(any(ContactForm.class))).thenReturn(false);

        mockMvc.perform(post("/contact")
                        .param("fullName", "John Doe")
                        .param("email", "john@example.com")
                        .param("subject", "Test Subject")
                        .param("message", "This is a test message with enough characters."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/#contact"))
                .andExpect(flash().attributeExists("errorMessage"));
    }
}
