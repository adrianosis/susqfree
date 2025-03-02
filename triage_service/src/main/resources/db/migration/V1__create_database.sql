CREATE TABLE triage (
    id SERIAL PRIMARY KEY,
    patient_id UUID NOT NULL,
    consultation_reason TEXT NOT NULL,
    symptom_duration TEXT,
    body_temperature DOUBLE PRECISION,
    blood_pressure VARCHAR(50),
    heart_rate VARCHAR(50),
    respiratory_rate VARCHAR(50),
    consciousness_state VARCHAR(50),
    pain_intensity VARCHAR(50),
    bleeding_present VARCHAR(50),
    continuous_medication_use VARCHAR(50),
    pregnant VARCHAR(50),
    allergies VARCHAR(50),
    priority VARCHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE triage_common_symptom (
   triage_id INT NOT NULL,
   common_symptom VARCHAR(30),
   FOREIGN KEY (triage_id) REFERENCES triage(id) ON DELETE CASCADE
);

CREATE TABLE triage_chronic_disease (
    triage_id INT NOT NULL,
    chronic_disease VARCHAR(30),
    FOREIGN KEY (triage_id) REFERENCES triage(id) ON DELETE CASCADE
);

CREATE TABLE triage_continuous_medication (
    triage_id INT NOT NULL,
    medication_name VARCHAR(30),
    FOREIGN KEY (triage_id) REFERENCES triage(id) ON DELETE CASCADE
);

CREATE TABLE triage_allergy (
    triage_id INT NOT NULL,
    allergy_name VARCHAR(30),
    FOREIGN KEY (triage_id) REFERENCES triage(id) ON DELETE CASCADE
);
