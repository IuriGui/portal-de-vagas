CREATE TABLE app_user (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password_hash VARCHAR(60) NOT NULL,
                          role VARCHAR(20) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE address (
                         id SERIAL PRIMARY KEY,
                         zip_code VARCHAR(9) NOT NULL,
                         state_code CHAR(2) NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         district VARCHAR(100) NOT NULL,
                         street VARCHAR(100) NOT NULL,
                         number VARCHAR(10) NOT NULL,
                         complement VARCHAR(100),
                         latitude DECIMAL(9, 6),
                         longitude DECIMAL(9, 6)
);

CREATE TABLE company (
                         id SERIAL PRIMARY KEY,
                         company_name VARCHAR(100) NOT NULL,
                         description TEXT,
                         phone VARCHAR(15),
                         main_address_id INT REFERENCES address(id),
                         pending_approval boolean not null default TRUE
);

CREATE TABLE recruiter_profile (
                                   id SERIAL PRIMARY KEY,
                                   user_id UUID NOT NULL UNIQUE REFERENCES app_user(id),
                                   company_id INT NOT NULL REFERENCES company(id),
                                   full_name VARCHAR(100) NOT NULL,
                                   job_title VARCHAR(100),
                                   company_role VARCHAR(20) NOT NULL DEFAULT 'MEMBER'
                                       CHECK (company_role IN ('ADMIN', 'MEMBER')),
                                   status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
                                       CHECK (status IN ('PENDING', 'ACTIVE', 'REJECTED')),
                                   created_at TIMESTAMP NOT NULL DEFAULT now(),
                                   updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE candidate_profile (
                                   id SERIAL PRIMARY KEY,
                                   user_id UUID NOT NULL UNIQUE REFERENCES app_user(id),
                                   full_name VARCHAR(100) NOT NULL,
                                   phone VARCHAR(15),
                                   address_id INT REFERENCES address(id),
                                   birth_date DATE NOT NULL,
                                   resume_url VARCHAR(255)
);

CREATE TABLE job_area (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE opportunity (
                             id SERIAL PRIMARY KEY,
                             recruiter_id INT NOT NULL REFERENCES recruiter_profile(id),
                             company_id INT NOT NULL REFERENCES company(id),
                             job_area_id INT NOT NULL REFERENCES job_area(id),
                             address_id INT REFERENCES address(id),
                             title VARCHAR(100) NOT NULL,
                             description TEXT NOT NULL,
                             published_at TIMESTAMP NOT NULL,
                             expires_at TIMESTAMP NOT NULL,
                             is_remote BOOLEAN NOT NULL,
                             workload_hours INT NOT NULL,
                             salary NUMERIC(12, 2),
                             benefits TEXT,
                             requirements TEXT
);

CREATE TABLE academic_background (
                                     id SERIAL PRIMARY KEY,
                                     candidate_id INT NOT NULL REFERENCES candidate_profile(id) ON DELETE CASCADE,
                                     institution_name VARCHAR(100) NOT NULL,
                                     course_name VARCHAR(100) NOT NULL,
                                     start_date DATE NOT NULL,
                                     end_date DATE
);

CREATE TABLE professional_experience (
                                         id SERIAL PRIMARY KEY,
                                         candidate_id INT NOT NULL REFERENCES candidate_profile(id) ON DELETE CASCADE,
                                         job_title VARCHAR(100) NOT NULL,
                                         company_name VARCHAR(100) NOT NULL,
                                         start_date DATE NOT NULL,
                                         end_date DATE
);

CREATE TABLE skill (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE candidate_skill (
                                 candidate_id INT NOT NULL REFERENCES candidate_profile(id) ON DELETE CASCADE,
                                 skill_id INT NOT NULL REFERENCES skill(id) ON DELETE CASCADE,
                                 PRIMARY KEY (candidate_id, skill_id)
);

CREATE TABLE application (
                             id SERIAL PRIMARY KEY,
                             candidate_id INT NOT NULL REFERENCES candidate_profile(id),
                             opportunity_id INT NOT NULL REFERENCES opportunity(id),
                             applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             status VARCHAR(50) NOT NULL DEFAULT 'APPLIED'
                                 CHECK (status IN ('APPLIED', 'VIEWED', 'REJECTED', 'INTERVIEW', 'HIRED', 'WITHDRAWN')),
                             UNIQUE(candidate_id, opportunity_id)
);