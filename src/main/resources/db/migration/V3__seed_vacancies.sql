-- Seed data for vacancies table
INSERT INTO vacancies (id, hiring_manager_id, recruiter_id, title, description, requirements, location, remote_allowed, seniority, status, salary_min, salary_max, currency, closed_at, created_at, updated_at)
VALUES
    -- Open vacancies
    ('550e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440001',
     'Senior Backend Developer',
     'We are looking for an experienced Backend Developer to join our engineering team. You will be responsible for designing and implementing scalable microservices.',
     'Java 17+, Spring Boot, PostgreSQL, Docker, Kubernetes, REST APIs, 5+ years experience',
     'Lima, Peru', true, 'SENIOR', 'OPEN',
     5000.00, 7000.00, 'USD', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('550e8400-e29b-41d4-a716-446655440002', '650e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440001',
     'Frontend Engineer',
     'Join our product team to build beautiful and responsive user interfaces. You will work closely with designers and backend developers.',
     'React, TypeScript, HTML5, CSS3, REST APIs, Git, 3+ years experience',
     'Remote', true, 'MID', 'OPEN',
     3500.00, 5000.00, 'USD', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('550e8400-e29b-41d4-a716-446655440003', '650e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440002',
     'DevOps Engineer',
     'We need a DevOps Engineer to help us automate our infrastructure and improve our deployment pipelines.',
     'AWS, Docker, Kubernetes, Terraform, CI/CD, Jenkins, GitLab CI, 4+ years experience',
     'Arequipa, Peru', false, 'SENIOR', 'OPEN',
     4500.00, 6500.00, 'USD', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('550e8400-e29b-41d4-a716-446655440004', '650e8400-e29b-41d4-a716-446655440003', '750e8400-e29b-41d4-a716-446655440002',
     'QA Automation Engineer',
     'Looking for a QA Engineer with strong automation skills to ensure quality across all our products.',
     'Selenium, Cypress, Jest, API Testing, Java or Python, 3+ years experience',
     'Lima, Peru', true, 'MID', 'OPEN',
     3000.00, 4500.00, 'USD', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('550e8400-e29b-41d4-a716-446655440005', '650e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440001',
     'Data Engineer',
     'Build and maintain data pipelines and analytics infrastructure for our growing data needs.',
     'Python, SQL, Apache Spark, Airflow, Data Warehousing, ETL, 4+ years experience',
     'Remote', true, 'SENIOR', 'OPEN',
     5500.00, 7500.00, 'USD', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Draft vacancy
    ('550e8400-e29b-41d4-a716-446655440006', '650e8400-e29b-41d4-a716-446655440001', NULL,
     'Mobile Developer',
     'We are planning to build a mobile app and need an experienced mobile developer.',
     'React Native or Flutter, iOS, Android, REST APIs, 3+ years experience',
     'Lima, Peru', true, 'MID', 'DRAFT',
     3500.00, 5500.00, 'USD', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Closed vacancy
    ('550e8400-e29b-41d4-a716-446655440007', '650e8400-e29b-41d4-a716-446655440003', '750e8400-e29b-41d4-a716-446655440002',
     'Junior Java Developer',
     'Entry level position for recent graduates passionate about software development.',
     'Java, Spring Framework, SQL, Git, Computer Science degree or equivalent',
     'Cusco, Peru', false, 'JUNIOR', 'CLOSED',
     2000.00, 3000.00, 'USD', CURRENT_TIMESTAMP - INTERVAL '5 days',
     CURRENT_TIMESTAMP - INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '5 days'),

    ('550e8400-e29b-41d4-a716-446655440008', '650e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440001',
     'Product Manager',
     'Lead product development and strategy for our core recruiting platform.',
     'Product Management, Agile, Stakeholder Management, Technical Background, 5+ years experience',
     'Lima, Peru', true, 'SENIOR', 'OPEN',
     4000.00, 6000.00, 'USD', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
