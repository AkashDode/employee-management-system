# Employee Management System
Spring Boot + Jenkins + SonarQube + S3 + MySQL RDS

Set `EMS_ADMIN_INITIAL_PASSWORD` in the application environment before first startup to create the initial admin account. Store it as a deployment secret; never commit its value. This only seeds a missing admin account and does not change an existing account's password.