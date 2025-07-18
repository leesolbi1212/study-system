-- Create database
CREATE DATABASE IF NOT EXISTS study_management;

USE study_management;

-- Users table
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Studies table
CREATE TABLE studies (
    study_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    max_participants INT NOT NULL,
    current_participants INT DEFAULT 0,
    deadline DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'RECRUITING',
    creator_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Study applications table
CREATE TABLE study_applications (
    application_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    study_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'APPLIED',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (study_id) REFERENCES studies(study_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY unique_application (study_id, user_id)
);

-- Indexes for performance
CREATE INDEX idx_studies_creator_id ON studies(creator_id);
CREATE INDEX idx_studies_status ON studies(status);
CREATE INDEX idx_studies_deadline ON studies(deadline);
CREATE INDEX idx_applications_study_id ON study_applications(study_id);
CREATE INDEX idx_applications_user_id ON study_applications(user_id);

-- Insert sample data
INSERT INTO users (username, email, password, full_name) VALUES
('admin', 'admin@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Administrator'),
('john_doe', 'john@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'John Doe'),
('jane_smith', 'jane@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Jane Smith');

INSERT INTO studies (title, description, max_participants, deadline, creator_id) VALUES
('Java Spring Boot 스터디', 'Spring Boot를 활용한 웹 개발 스터디입니다.', 5, '2024-01-31', 1),
('React 프론트엔드 스터디', 'React를 이용한 모던 프론트엔드 개발 스터디입니다.', 4, '2024-02-15', 2),
('알고리즘 문제풀이 스터디', '코딩테스트 준비를 위한 알고리즘 스터디입니다.', 6, '2024-03-01', 1);

select * from users;
select * from study_applications;
select * from studies;