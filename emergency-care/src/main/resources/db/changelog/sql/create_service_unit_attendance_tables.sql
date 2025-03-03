CREATE TABLE IF NOT EXISTS service_unit (
    id BIGSERIAL PRIMARY KEY,
    service_type VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL,
    unit_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS attendance (
    id BIGSERIAL PRIMARY KEY,
    patient_id VARCHAR(20) NOT NULL,
    service_unit_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (service_unit_id) REFERENCES service_unit(id)
);

CREATE TABLE IF NOT EXISTS attendance_history (
    id BIGSERIAL PRIMARY KEY,
    patient_id VARCHAR(20) NOT NULL,
    service_unit_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (service_unit_id) REFERENCES service_unit(id)
);
