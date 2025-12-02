package com.staffinity.recruiting.candidates.infrastructure.adapters;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Profile("test")
public class TestEmployeeClientImpl implements EmployeeClient {

    @Override
    public List<Employee> getEmployees() {
        // Return mock data for tests
        return Arrays.asList(
                new Employee(
                        UUID.randomUUID(),
                        "EMP001",
                        "John Doe",
                        "john.doe@company.com",
                        "hashedpassword",
                        "+1234567890",
                        LocalDate.of(1985, 5, 15),
                        LocalDate.of(2010, 1, 1),
                        UUID.randomUUID(),
                        "123456789",
                        null,
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        OffsetDateTime.now(),
                        OffsetDateTime.now(),
                        false
                ),
                new Employee(
                        UUID.randomUUID(),
                        "EMP002",
                        "Jane Smith",
                        "jane.smith@company.com",
                        "hashedpassword2",
                        "+0987654321",
                        LocalDate.of(1990, 8, 20),
                        LocalDate.of(2015, 6, 15),
                        UUID.randomUUID(),
                        "987654321",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        OffsetDateTime.now(),
                        OffsetDateTime.now(),
                        false
                )
        );
    }
}
