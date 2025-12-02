# Quick Start Guide

## Prerequisites Check

Before running the application, ensure you have:

1. **Java 17 or higher** installed
   ```bash
   java -version
   # Should show: openjdk version "17" or higher
   ```

2. **Maven 3.6+** installed (or use the Maven wrapper if provided)
   ```bash
   mvn -version
   # Should show: Apache Maven 3.6.x or higher
   ```

## Installation Steps

### 1. Navigate to Project Directory
```bash
cd /data/workspace/qoder-demo
```

### 2. Configure Email Settings (Important!)

Edit `src/main/resources/application.properties`:

**Option A: Use Gmail (Recommended for testing)**
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

app.contact.recipient-email=your-email@gmail.com
app.contact.from-email=noreply@yourcompany.com
```

**Note:** For Gmail, you must:
1. Enable 2-factor authentication in your Google account
2. Generate an App Password: Google Account → Security → App Passwords
3. Use the App Password (not your regular password)

**Option B: Use Fake SMTP for Development (No real emails)**
```properties
spring.mail.host=localhost
spring.mail.port=1025
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false

app.contact.recipient-email=test@example.com
app.contact.from-email=noreply@example.com
```

Then run a fake SMTP server (optional):
```bash
# Using Docker
docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog

# View emails at: http://localhost:8025
```

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR directly:
```bash
java -jar target/landing-page-1.0.0.jar
```

### 5. Access the Application

Open your browser and go to:
```
http://localhost:8080
```

## Testing the Application

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=LandingPageControllerTest
mvn test -Dtest=ContactServiceTest
```

### Test the Contact Form

1. Navigate to http://localhost:8080
2. Scroll down to the Contact section
3. Fill in the form:
   - **Full Name:** John Doe (2-100 characters, letters only)
   - **Email:** john@example.com (valid email format)
   - **Phone:** +1 (555) 123-4567 (optional)
   - **Subject:** Test Inquiry (5-200 characters)
   - **Message:** This is a test message. (10-1000 characters)
4. Click "Send Message"
5. You should see a success message

## Common Issues and Solutions

### Issue: Port 8080 already in use
**Solution:** Change the port in `application.properties`:
```properties
server.port=8081
```

### Issue: Email not sending
**Solutions:**
1. Check SMTP credentials are correct
2. For Gmail, verify App Password is used
3. Check firewall allows SMTP connections
4. Review application logs: `tail -f logs/application.log`
5. Use fake SMTP for development testing

### Issue: Application won't start
**Solutions:**
1. Verify Java 17+ is installed: `java -version`
2. Clean and rebuild: `mvn clean install`
3. Check for compilation errors in console
4. Ensure all dependencies are downloaded

### Issue: Maven not found
**Solution:** Install Maven:
```bash
# Ubuntu/Debian
sudo apt-get install maven

# macOS
brew install maven

# Or download from: https://maven.apache.org/download.cgi
```

## Development Mode

For development with auto-reload:

```bash
mvn spring-boot:run
```

Spring Boot DevTools is included and will automatically:
- Restart the application on code changes
- Disable template caching for immediate updates
- Provide enhanced development experience

## Production Deployment

### Build for Production
```bash
mvn clean package -DskipTests
```

### Run in Production
```bash
java -jar target/landing-page-1.0.0.jar
```

### Production Configuration

Create `application-prod.properties`:
```properties
server.port=8080
spring.thymeleaf.cache=true
logging.level.root=WARN
logging.level.com.example.landingpage=INFO

# Use environment variables for sensitive data
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
app.contact.recipient-email=${CONTACT_EMAIL}
```

Run with production profile:
```bash
java -jar target/landing-page-1.0.0.jar --spring.profiles.active=prod
```

## Health Check

Check application health:
```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{"status":"UP"}
```

## Customization

### Change Company Name
Edit `src/main/resources/templates/index.html`:
- Line 34: `<a href="#home">YourCompany</a>`
- Update all occurrences of "YourCompany"

### Change Colors
Edit `src/main/resources/static/css/style.css`:
```css
:root {
    --primary-color: #2563eb;  /* Change this */
    --primary-dark: #1e40af;   /* And this */
}
```

### Update Content
All content is in `src/main/resources/templates/index.html`:
- Hero section: Lines 42-58
- Features: Lines 62-138
- About: Lines 142-182
- Contact info: Lines 204-245

## Next Steps

1. ✅ Update company branding (name, logo, colors)
2. ✅ Customize content sections
3. ✅ Configure production email settings
4. ✅ Add your actual images (replace SVG placeholders)
5. ✅ Set up analytics (Google Analytics, etc.)
6. ✅ Configure domain and SSL certificate
7. ✅ Deploy to production server

## Support

For help:
- Check the main README.md
- Review application logs
- Check Spring Boot documentation: https://spring.io/projects/spring-boot

---

**Happy Coding! 🚀**
