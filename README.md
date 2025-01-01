# Patient Registration System

## How to Run the Project

### Option 1: Using Docker (Recommended)

1. Clone the repository:
   ```bash
   git clone https://github.com/fpesce27/patientRegistration.git
   cd patientRegistration
   ```

2. Make sure you have Docker and Docker Compose installed.

3. Start the application using Docker Compose:
   ```bash
   docker-compose up
   ```

4. The application will be available at `http://localhost:8080`.

### Option 2: Running Locally

1. Clone the repository:
   ```bash
    git clone https://github.com/fpesce27/patientRegistration.git
    cd patientRegistration
   ```

2. Make sure you have the following dependencies installed:
   - Java (version 21 or higher) 
   - Maven
   - MySQL
   - LocalStack

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. The application will be available at `http://localhost:8080`.

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`.

## Design Decisions

- **Patient**:
  - Besides the required fields, `Patient` extends from an `AuditableEntity` class to store the creation and update timestamps, also the `deletedAt` timestamp for soft deletes.
  - It has an `externalId` field so that external systems can reference the patient using a unique identifier and avoid exposing the database ID.
  - It has an embedded `Address` object to store the patient's address.

- **Validation**:
  - Input data is validated using the Jakarta Validation API.
  - Custom validation annotations are used to enforce constraints on the `Patient` entity.
  - Also some custom validations where implemented to check if the patient's `email` or `phoneNumber` are already registered, or to validate the `address` field.
    - This was implemented via strategy with an interface `PatientRegisterValidation` so that it can be easily extended with new validations.

- **Document Photo**:
  - The photo is stored in an S3 bucket, and the URL is saved in the database.
  - For development and testing, LocalStack is used to mock the S3 service.
  - A `DocumentService` was created that uses the `S3Service` to upload the photo to the bucket.
    - This was made so that in the future, if a new service is used to store the photos, it can be easily implemented and changed.

- **Notifications**:
  - Due to future SMS notifications, a `NotificationService` interface was created.
  - The `EmailService` implementation sends an email to the patient when they are registered via `Mailtrap`.

### **Services Class Diagram**
![Class Diagram](https://www.plantuml.com/plantuml/png/jLJ1Rjim3BtxAuIUdAtPfJUZ21giBR0XwOgssrsrc4oO91kI720B-k-b8rcLBUNMd19HZtplqQHkZANQTb8KHIseCUIHMWxAFeFUyHh8ePYJreMUe6qCjutULuYQX6BObXiB-WyLd66eKQOYAmnTFvu2B31RDPPlU7qytzFQqJoaGOIofkxaXyQAt8u3nLUYFOUdBDrP7mlTlloA38pYit21X-AjABZ28XjQG-gO7DxIwzGREQ2SdJj2wNiKQ91mrt26LBxzIyOq61FbSzaAwDsPJoFnyJj9kJWpqzz6mKCdNq6VbIWtASVPDMuaS0JNjBsEKI_x5Yv7UNvy5aYy1PkvAItcL6HQ1SLamAFnoG5wXsxizoVz9KfwldAMQlZenF6hUeguqta5S-BWYe6ko4-we-jm_aHvt-xMon-h87AwiUvJNOM5VXx-5aZHjQAXRCWeX-t7Bze9orkgxJqNCCCigxdQOWO30HQIZDzFgm6Kp4zWz5A-2sTuAUb_xDSL1b-x-X-wfH82bMj0Ju-romt-njT1KZ3H8nFO9UaZgPrUO32JPevTWEhajEjkmaBd9o_xVBv87xKSwEBBnUarpILaTZ43IrxjJ5fIBeSDDHVfLaGSWJSQhXjGh9FY7G00)

## Future Implementations

- **SMS Notifications**:
  - Implement a `SmsService` to send SMS notifications to the patient when they are registered.
    - This would be as simple as creating a new implementation of the `NotificationService` interface, and a new `NotificationType` enum value.
    - Also the PatientRegistrationDTO has a `notificationType` field to specify the type of notification to be sent, which by default is `EMAIL`.
        - This was made so that the notification type selected can change in the future without changing the code.
## Technologies

- **Spring Boot**: Framework for building the application.
- **Hibernate**: ORM tool for database interaction.
- **JPA**: Abstraction layer for persistence.
- **MySQL**: Relational database for production.
- **LocalStack**: Mock AWS services for development and testing.
- **Jakarta Validation**: Validation framework for input data.
- **Maven**: Build tool for managing dependencies and the project lifecycle.
- **JUnit and Mockito**: Testing frameworks for unit and integration tests.
- **Docker**: Containerization for consistent environments.
- **Docker Compose**: Tool for defining and managing multi-container applications.
