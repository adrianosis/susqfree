# SUSQFree Project 

## 1. Introduction
The **SUSQFree** is a distributed system based on microservices for efficient management of health units, doctors, patients, and appointments, including triage and emergency care.

## 2. Objectives

The **SUSQFree** system is designed to intelligently prioritize patient care, reduce waiting times, and alleviate the burden on healthcare facilities. It also includes appointment scheduling, enabling patients to book consultations based on the availability of healthcare units, thus optimizing service flow.

The initiative aims to solve not just a logistical issue but also to humanize patient care, ensuring that individuals receive necessary attention at the right time. The project aims to:
- **Enhance patient experience** by providing predictability and transparency in care, reducing stress, and increasing satisfaction.
- **Ensure prioritization of critical cases, saving lives.**
- **Optimize patient flow** to reduce queues and minimize overcrowding.
- **Provide digital tools** that help organize appointments, allowing healthcare professionals to focus on saving lives instead of dealing with administrative inefficiencies.

## 3. Technologies Used
- **Backend:** Spring Boot, Spring Data JPA/MongoDB, OpenFeign
- **Database:** PostgreSQL, MongoDB, H2 (tests)
- **Database Management:** Flyway, Liquibase
- **Testing:** JUnit, Mockito, Testcontainers, WireMock
- **Documentation:** Swagger
- **Security:** Amazon Cognito, OAuth2, JWT
- **Infrastructure:** Spring Cloud Eureka, Spring Cloud Gateway

## 4. Architecture
The system follows a **Clean Architecture** approach and is divided into multiple microservices, each responsible for a specific functionality.

## 5. System Features

### 5.1 Doctor Management Microservice (**doctor-management**)
**Purpose:** Manages doctors and their specialties.

**Endpoints:**
- **Doctors:**
  - `POST /doctors` - Creates a new doctor.
  - `GET /doctors/{id}` - Retrieves a specific doctor.
  - `PUT /doctors/{id}` - Updates a specific doctor.
  - `DELETE /doctors/{id}` - Deletes a specific doctor.
  - `GET /doctors/specialty/{name}` - Returns doctors by specialty.
- **Specialties:**
  - `POST /api/specialties` - Registers a new medical specialty.
  - `GET /api/specialties` - Lists all specialties.
  - `GET /api/specialties/{id}` - Retrieves details of a specialty.
  - `PUT /api/specialties/{id}` - Updates a specialty.
  - `DELETE /api/specialties/{id}` - Removes a specialty.

### 5.2 Health Unit Management Microservice (**health-unit-management**)
**Purpose:** Manages basic information and locations of health units.

**Endpoints:**
- `POST /api/health-unit` - Creates a new health unit.
- `GET /api/health-unit` - Lists all health units.
- `GET /api/health-unit/{id}` - Retrieves a specific health unit.
- `PUT /api/health-unit/{id}` - Updates a health unit.

### 5.3 Patient Management Microservice (**patient-management**)
**Purpose:** Manages patients, storing basic personal information.

**Endpoints:**
- `POST /api/patient` - Creates a new patient.
- `GET /api/patient` - Lists all patients.
- `GET /api/patient/{id}` - Retrieves a patient by ID.
- `GET /api/patient/cpf/{cpf}` - Retrieves a patient by CPF.
- `PUT /api/patient/{id}` - Updates a specific patient.

### 5.4 Appointment Scheduling Microservice (**schedule-management**)
**Purpose:** Manages medical appointment scheduling.

**Endpoints:**
- `POST /api/appointments` - Creates a new appointment.
- `PUT /api/appointments/confirm` - Confirms an appointment.
- `PUT /api/appointments/{appointmentId}/complete` - Completes an appointment.
- `GET /api/appointments/available` - Returns available appointments.
- `GET /api/appointments/health-unit/{healthUnitId}` - Lists appointments by health unit.
- `GET /api/appointments/patient/{patientId}` - Lists appointments by patient.
- `DELETE /api/appointments/{appointmentId}` - Cancels an appointment.
- **Doctor Appointments:**
  - `POST /api/appointments/doctor` - Creates doctor appointments.
  - `DELETE /api/appointments/doctor` - Cancels doctor appointments.
  - `GET /api/appointments/doctor/{doctorId}` - Lists appointments for a doctor.

### 5.5 Triage Microservice (**triage-service**)
**Purpose:** Manages patient triage processes.

**Endpoints:**
- `POST /triage` - Performs a triage and returns the priority level.
- `GET /triage` - Lists all triages.
- `GET /triage/questions` - Returns triage questions.
- `GET /triage/by-patient/{patientId}` - Lists triages for a patient.

### 5.6 Emergency Care Microservice (**emergency-care-service**)
**Purpose:** Manages emergency care and patient prioritization.

**Endpoints:**
- `POST /attendances/{serviceUnitId}` - Registers an attendance based on triage.
- `POST /attendances/call-next/{serviceUnitId}` - Calls the next patient in the queue.
- `DELETE /attendances/{id}/complete` - Marks an attendance as completed.
- `DELETE /attendances/{id}/cancel` - Cancels an attendance.
- **Service Units:**
  - `POST /service-units` - Registers a service unit.
  - `GET /service-units/{id}` - Retrieves a service unit by ID.
  - `GET /service-units` - Lists all service units.

### 5.7 Eureka Server Microservice
**Purpose:** Discovery service for dynamic microservice registration.

### 5.8 Cloud Gateway Microservice
**Purpose:** Central entry point for requests, with authentication via Amazon Cognito and OAuth2.

## 6. Getting Started
To set up the project locally:

1. Clone the repository:
2. Start the required databases using Docker  (from `infra/docker`) :
   ```sh
   docker-compose up -d
   ```
3. Run each microservice using your preferred IDE or:
   ```sh
   mvn spring-boot:run
   ```

