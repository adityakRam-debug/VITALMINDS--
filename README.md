# VitalMinds V7

A student-friendly personal health advisory and health-management platform built with **HTML, CSS, JavaScript, Java 25, Spring Boot, Spring Security, JPA/Hibernate, MySQL and PDFBox**.

## V7 highlights
- Login/register with BCrypt passwords and account-scoped data
- Profile: age, gender, height, weight and health goal
- Health report PDF upload and text extraction
- Optional OCR for scanned PDFs with Tesseract
- Extraction of hemoglobin, glucose, cholesterol, blood pressure and BMI
- Dynamic Vital Score and wellness recommendations
- Health trends with Chart.js
- AI health assistant with optional OpenAI-compatible endpoint and local fallback
- Activity and sleep logs
- Hydration tracker
- Meal/nutrition log
- Workout planner
- Medication and reminders
- Doctor appointment log
- Health alert center for values that deserve attention
- CSV export
- Generated PDF wellness summary
- Basic ADMIN console at `/admin`
- Responsive modern UI

## Requirements
- Java 25+
- Maven 3.6.3+
- MySQL 8+
- Optional: Tesseract OCR for scanned reports

## 1. Database
```sql
CREATE DATABASE vitalminds;
```

## 2. Configure MySQL
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Hibernate will create/update the V7 tables automatically with `ddl-auto=update`.

## 3. Run
```bash
mvn clean package
mvn spring-boot:run
```
Open:
`http://localhost:8080/register`

## 4. Optional OCR on macOS
```bash
brew install tesseract
```
Then keep:
```properties
vitalminds.ocr.enabled=true
```

## 5. Optional AI assistant
V7 works without an external AI service. To enable an OpenAI-compatible chat endpoint:
```bash
export VITALMINDS_AI_ENABLED=true
export OPENAI_API_KEY="YOUR_API_KEY"
export VITALMINDS_AI_MODEL="gpt-5-mini"
mvn spring-boot:run
```
The project does not hard-code an API key.

## 6. Admin console
New registrations default to role `USER`. For a local project demo, promote an account after registration:
```sql
UPDATE users SET role='ADMIN' WHERE email='your-email@example.com';
```
Then log out and log back in. Visit:
`http://localhost:8080/admin`

## Project structure
```text
VitalMinds-v7/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/vitalminds/
    │   ├── config/
    │   ├── controller/
    │   ├── model/
    │   ├── repository/
    │   └── service/
    └── resources/
        ├── application.properties
        ├── static/
        └── templates/
```

## Important health-data limitation
This is an educational prototype. Automatically extracted laboratory values and AI explanations can be wrong. The app must not be used to diagnose illness or change medication. Production deployment would require stronger security, CSRF protection, secure/private file storage, audit logging, encryption where appropriate, robust OCR/table parsing, reference-range validation, consent/privacy controls and clinical validation.
