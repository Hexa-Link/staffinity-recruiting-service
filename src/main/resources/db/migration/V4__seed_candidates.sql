-- Seed data for candidates table
INSERT INTO candidates (id, vacancy_id, first_name, last_name, email, phone_number, resume_url, linkedin_url, portfolio_url, application_status, source, rejection_reason, created_at, updated_at)
VALUES
    -- Candidates for Senior Backend Developer vacancy (550e8400-e29b-41d4-a716-446655440001)
    ('850e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001',
     'Carlos', 'Rodriguez', 'carlos.rodriguez@email.com', '+51987654321',
     'https://resume.io/carlos-rodriguez', 'https://linkedin.com/in/carlos-rodriguez', 'https://github.com/carlos-rodriguez',
     'INTERVIEWING', 'LINKEDIN', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('850e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440001',
     'Maria', 'Gonzales', 'maria.gonzales@email.com', '+51987654322',
     'https://resume.io/maria-gonzales', 'https://linkedin.com/in/maria-gonzales', NULL,
     'UNDER_REVIEW', 'COMPANY_WEBSITE', NULL,
     CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '1 day'),

    ('850e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440001',
     'Jorge', 'Ramirez', 'jorge.ramirez@email.com', '+51987654323',
     'https://resume.io/jorge-ramirez', 'https://linkedin.com/in/jorge-ramirez', 'https://jramirez.dev',
     'REJECTED', 'REFERRAL', 'Insufficient experience with microservices architecture',
     CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '3 days'),

    -- Candidates for Frontend Engineer vacancy (550e8400-e29b-41d4-a716-446655440002)
    ('850e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440002',
     'Ana', 'Silva', 'ana.silva@email.com', '+51987654324',
     'https://resume.io/ana-silva', 'https://linkedin.com/in/ana-silva', 'https://anasilva.portfolio.com',
     'OFFER_EXTENDED', 'LINKEDIN', NULL,
     CURRENT_TIMESTAMP - INTERVAL '10 days', CURRENT_TIMESTAMP - INTERVAL '1 day'),

    ('850e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440002',
     'Luis', 'Fernandez', 'luis.fernandez@email.com', '+51987654325',
     'https://resume.io/luis-fernandez', 'https://linkedin.com/in/luis-fernandez', NULL,
     'SUBMITTED', 'COMPANY_WEBSITE', NULL,
     CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '1 day'),

    ('850e8400-e29b-41d4-a716-446655440006', '550e8400-e29b-41d4-a716-446655440002',
     'Patricia', 'Torres', 'patricia.torres@email.com', '+51987654326',
     'https://resume.io/patricia-torres', 'https://linkedin.com/in/patricia-torres', 'https://ptorres.dev',
     'INTERVIEWING', 'REFERRAL', NULL,
     CURRENT_TIMESTAMP - INTERVAL '7 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),

    -- Candidates for DevOps Engineer vacancy (550e8400-e29b-41d4-a716-446655440003)
    ('850e8400-e29b-41d4-a716-446655440007', '550e8400-e29b-41d4-a716-446655440003',
     'Roberto', 'Mendoza', 'roberto.mendoza@email.com', '+51987654327',
     'https://resume.io/roberto-mendoza', 'https://linkedin.com/in/roberto-mendoza', NULL,
     'UNDER_REVIEW', 'LINKEDIN', NULL,
     CURRENT_TIMESTAMP - INTERVAL '3 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),

    ('850e8400-e29b-41d4-a716-446655440008', '550e8400-e29b-41d4-a716-446655440003',
     'Carmen', 'Vargas', 'carmen.vargas@email.com', '+51987654328',
     'https://resume.io/carmen-vargas', 'https://linkedin.com/in/carmen-vargas', 'https://cvargas.tech',
     'INTERVIEWING', 'COMPANY_WEBSITE', NULL,
     CURRENT_TIMESTAMP - INTERVAL '8 days', CURRENT_TIMESTAMP - INTERVAL '4 days'),

    -- Candidates for QA Automation Engineer vacancy (550e8400-e29b-41d4-a716-446655440004)
    ('850e8400-e29b-41d4-a716-446655440009', '550e8400-e29b-41d4-a716-446655440004',
     'Diego', 'Castillo', 'diego.castillo@email.com', '+51987654329',
     'https://resume.io/diego-castillo', 'https://linkedin.com/in/diego-castillo', NULL,
     'SUBMITTED', 'LINKEDIN', NULL,
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('850e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440004',
     'Sofia', 'Morales', 'sofia.morales@email.com', '+51987654330',
     'https://resume.io/sofia-morales', 'https://linkedin.com/in/sofia-morales', 'https://github.com/sofia-morales',
     'UNDER_REVIEW', 'REFERRAL', NULL,
     CURRENT_TIMESTAMP - INTERVAL '4 days', CURRENT_TIMESTAMP - INTERVAL '3 days'),

    -- Candidates for Data Engineer vacancy (550e8400-e29b-41d4-a716-446655440005)
    ('850e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440005',
     'Miguel', 'Herrera', 'miguel.herrera@email.com', '+51987654331',
     'https://resume.io/miguel-herrera', 'https://linkedin.com/in/miguel-herrera', NULL,
     'INTERVIEWING', 'LINKEDIN', NULL,
     CURRENT_TIMESTAMP - INTERVAL '6 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),

    ('850e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440005',
     'Isabella', 'Rojas', 'isabella.rojas@email.com', '+51987654332',
     'https://resume.io/isabella-rojas', 'https://linkedin.com/in/isabella-rojas', 'https://irojas.datascience.io',
     'REJECTED', 'COMPANY_WEBSITE', 'Limited experience with Apache Spark',
     CURRENT_TIMESTAMP - INTERVAL '12 days', CURRENT_TIMESTAMP - INTERVAL '8 days'),

    ('850e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440005',
     'Fernando', 'Chavez', 'fernando.chavez@email.com', '+51987654333',
     'https://resume.io/fernando-chavez', 'https://linkedin.com/in/fernando-chavez', NULL,
     'UNDER_REVIEW', 'REFERRAL', NULL,
     CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '1 day'),

    -- Candidates for Product Manager vacancy (550e8400-e29b-41d4-a716-446655440008)
    ('850e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440008',
     'Valeria', 'Ortiz', 'valeria.ortiz@email.com', '+51987654334',
     'https://resume.io/valeria-ortiz', 'https://linkedin.com/in/valeria-ortiz', NULL,
     'INTERVIEWING', 'LINKEDIN', NULL,
     CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '3 days'),

    ('850e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440008',
     'Andres', 'Medina', 'andres.medina@email.com', '+51987654335',
     'https://resume.io/andres-medina', 'https://linkedin.com/in/andres-medina', 'https://amedina.pm',
     'SUBMITTED', 'COMPANY_WEBSITE', NULL,
     CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '1 day'),

    -- Candidates for closed vacancy (550e8400-e29b-41d4-a716-446655440007)
    ('850e8400-e29b-41d4-a716-446655440016', '550e8400-e29b-41d4-a716-446655440007',
     'Gabriel', 'Rios', 'gabriel.rios@email.com', '+51987654336',
     'https://resume.io/gabriel-rios', 'https://linkedin.com/in/gabriel-rios', NULL,
     'HIRED', 'UNIVERSITY_PARTNERSHIP', NULL,
     CURRENT_TIMESTAMP - INTERVAL '25 days', CURRENT_TIMESTAMP - INTERVAL '5 days'),

    ('850e8400-e29b-41d4-a716-446655440017', '550e8400-e29b-41d4-a716-446655440007',
     'Camila', 'Cruz', 'camila.cruz@email.com', '+51987654337',
     'https://resume.io/camila-cruz', 'https://linkedin.com/in/camila-cruz', NULL,
     'REJECTED', 'UNIVERSITY_PARTNERSHIP', 'Position filled by another candidate',
     CURRENT_TIMESTAMP - INTERVAL '28 days', CURRENT_TIMESTAMP - INTERVAL '5 days');
