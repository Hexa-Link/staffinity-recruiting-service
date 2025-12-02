package com.staffinity.recruiting.candidates.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Profile("!test")
public class EmployeeClientImpl implements EmployeeClient {

    private final WebClient webClient;

    public EmployeeClientImpl(@Value("${external-services.personal-service.url}") String personalServiceUrl,
                              WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(personalServiceUrl).build();
    }

    @Override
    public List<Employee> getEmployees() {
        return webClient.get()
                .uri("/employees")
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();
    }
}
