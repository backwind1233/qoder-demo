package com.example.landingpage.model;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Contact form model with validation constraints.
 */
@Data
public class ContactForm {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
    private String fullName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Pattern(regexp = "^$|^[0-9\\s\\-\\+\\(\\)]+$", message = "Please provide a valid phone number")
    private String phoneNumber;

    @NotBlank(message = "Subject is required")
    @Size(min = 5, max = 200, message = "Subject must be between 5 and 200 characters")
    private String subject;

    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 1000, message = "Message must be between 10 and 1000 characters")
    private String message;
}
