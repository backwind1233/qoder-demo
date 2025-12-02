# Spring Boot Landing Page

A modern, responsive landing page built with Spring Boot and Thymeleaf, featuring a contact form with email notifications.

## Features

- ✨ Modern and responsive design with mobile-first approach
- 📱 Fully responsive layout (mobile, tablet, desktop)
- 📧 Contact form with email notifications
- ✅ Server-side form validation
- 🎨 Clean and professional UI
- 🚀 Fast performance and SEO-optimized
- ♿ Accessibility compliant (WCAG 2.1)
- 🔒 Security features (XSS protection, input sanitization)

## Technology Stack

- **Backend:** Spring Boot 3.2.0
- **Template Engine:** Thymeleaf
- **Build Tool:** Maven
- **Java Version:** 17
- **Frontend:** HTML5, CSS3, JavaScript (Vanilla)

## Project Structure

```
landing-page/
├── src/
│   ├── main/
│   │   ├── java/com/example/landingpage/
│   │   │   ├── config/
│   │   │   │   └── AsyncConfig.java
│   │   │   ├── controller/
│   │   │   │   └── LandingPageController.java
│   │   │   ├── model/
│   │   │   │   └── ContactForm.java
│   │   │   ├── service/
│   │   │   │   ├── ContactService.java
│   │   │   │   └── EmailService.java
│   │   │   └── LandingPageApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   ├── js/
│   │       │   │   └── script.js
│   │       │   └── images/
│   │       │       ├── hero-image.svg
│   │       │       ├── about-image.svg
│   │       │       └── favicon.png
│   │       ├── templates/
│   │       │   └── index.html
│   │       └── application.properties
│   └── test/
│       └── java/com/example/landingpage/
├── pom.xml
└── README.md
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd qoder-demo
   ```

2. **Configure email settings:**
   
   Edit `src/main/resources/application.properties` and update the email configuration:
   
   ```properties
   # For Gmail (requires App Password)
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   
   # Contact form recipients
   app.contact.recipient-email=admin@example.com
   app.contact.from-email=noreply@example.com
   ```

   **Note:** For Gmail, you need to:
   - Enable 2-factor authentication
   - Generate an App Password (Google Account > Security > App Passwords)
   
   **For Development/Testing:**
   You can use fake SMTP services like:
   - [Mailhog](https://github.com/mailhog/MailHog) (local testing)
   - [Mailtrap](https://mailtrap.io/) (online testing)

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application:**
   
   Open your browser and navigate to: `http://localhost:8080`

## Configuration

### Application Settings

Key configuration properties in `application.properties`:

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | 8080 | Application port |
| `spring.thymeleaf.cache` | false | Thymeleaf template caching (disable for dev) |
| `app.contact.recipient-email` | admin@example.com | Email recipient for contact forms |
| `app.contact.from-email` | noreply@example.com | Sender email address |

### Email Configuration Options

**Gmail:**
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
```

**Outlook/Office365:**
```properties
spring.mail.host=smtp.office365.com
spring.mail.port=587
```

**Custom SMTP:**
```properties
spring.mail.host=your-smtp-host
spring.mail.port=your-smtp-port
spring.mail.username=your-username
spring.mail.password=your-password
```

## Usage

### Landing Page Sections

The landing page includes:

1. **Navigation Bar** - Smooth scrolling navigation with mobile menu
2. **Hero Section** - Eye-catching headline with call-to-action buttons
3. **Features Section** - Showcase of 6 key features with icons
4. **About Section** - Company information with statistics
5. **Contact Section** - Contact form with validation
6. **Footer** - Links, social media, and legal information

### Contact Form

The contact form includes:
- **Full Name** (required, 2-100 characters)
- **Email** (required, valid email format)
- **Phone Number** (optional, valid phone format)
- **Subject** (required, 5-200 characters)
- **Message** (required, 10-1000 characters)

All fields have client-side and server-side validation.

## Customization

### Branding

1. **Company Name:** Update in `src/main/resources/templates/index.html`
2. **Colors:** Modify CSS variables in `src/main/resources/static/css/style.css`:
   ```css
   :root {
       --primary-color: #2563eb;
       --primary-dark: #1e40af;
       /* ... other colors */
   }
   ```
3. **Content:** Edit the HTML content in the template file

### Adding New Sections

Add new sections to `index.html` and update:
- Navigation links in the navbar
- CSS styles in `style.css`
- JavaScript for smooth scrolling in `script.js`

## Development

### Run in Development Mode

```bash
mvn spring-boot:run
```

The application will auto-reload on code changes (thanks to Spring Boot DevTools).

### Building for Production

```bash
mvn clean package
```

This creates an executable JAR file in the `target/` directory.

### Running the Production Build

```bash
java -jar target/landing-page-1.0.0.jar
```

## Testing

Run all tests:
```bash
mvn test
```

## Deployment

### JAR Deployment

1. Build the application: `mvn clean package`
2. Copy `target/landing-page-1.0.0.jar` to your server
3. Run: `java -jar landing-page-1.0.0.jar`

### Docker Deployment (Optional)

Create a `Dockerfile`:
```dockerfile
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/landing-page-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build and run:
```bash
docker build -t landing-page .
docker run -p 8080:8080 landing-page
```

### Cloud Deployment

This application can be deployed to:
- **Heroku** - Add a `Procfile`
- **AWS Elastic Beanstalk** - Upload the JAR file
- **Google Cloud Platform** - Use App Engine
- **Azure** - Use App Service

## Security Considerations

- ✅ Input validation (client and server-side)
- ✅ XSS protection through input sanitization
- ✅ CSRF protection (can be enabled with Spring Security)
- ✅ Secure email configuration
- ⚠️ Remember to use HTTPS in production
- ⚠️ Store sensitive credentials in environment variables

## Performance Optimization

- ✅ Gzip compression enabled
- ✅ Static resource caching
- ✅ Asynchronous email sending
- ✅ Optimized images (SVG format)
- ✅ Minimal JavaScript dependencies

## Browser Support

- ✅ Chrome (latest)
- ✅ Firefox (latest)
- ✅ Safari (latest)
- ✅ Edge (latest)
- ✅ Mobile browsers (iOS Safari, Chrome Mobile)

## Troubleshooting

### Email not sending

1. Check SMTP credentials in `application.properties`
2. Verify firewall/network allows SMTP connections
3. For Gmail, ensure App Password is used (not regular password)
4. Check application logs for errors

### Application won't start

1. Ensure Java 17+ is installed: `java -version`
2. Verify port 8080 is not in use
3. Check Maven build completed successfully
4. Review console logs for error messages

### Styling issues

1. Clear browser cache
2. Check browser console for CSS/JS errors
3. Verify static resources are loading correctly

## License

This project is licensed under the MIT License.

## Support

For issues and questions:
- Check the documentation above
- Review application logs
- Open an issue on the project repository

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

---

**Built with ❤️ using Spring Boot and Thymeleaf**
