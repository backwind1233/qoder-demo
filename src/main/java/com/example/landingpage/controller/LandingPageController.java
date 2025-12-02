package com.example.landingpage.controller;

import com.example.landingpage.model.ContactForm;
import com.example.landingpage.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for the landing page and contact form handling.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class LandingPageController {

    private final ContactService contactService;

    /**
     * Displays the landing page.
     */
    @GetMapping("/")
    public String showLandingPage(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "index";
    }

    /**
     * Processes contact form submission.
     */
    @PostMapping("/contact")
    public String submitContactForm(
            @Valid @ModelAttribute("contactForm") ContactForm contactForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            log.warn("Contact form validation failed: {}", bindingResult.getAllErrors());
            model.addAttribute("showContactSection", true);
            return "index";
        }

        // Process the contact form
        boolean success = contactService.processContactForm(contactForm);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", 
                "Thank you for contacting us! We'll get back to you soon.");
            log.info("Contact form submitted successfully by: {}", contactForm.getEmail());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "There was an error processing your request. Please try again later.");
            log.error("Failed to process contact form for: {}", contactForm.getEmail());
        }

        return "redirect:/#contact";
    }
}
