package com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class EmployeeClientImpl implements EmployeeClient {

    private final WebClient webClient;

    public EmployeeClientImpl(String personalServiceUrl, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(personalServiceUrl).build();
    }

    @Override
    public List<Employee> getEmployees() {
        return webClient.get()
            .uri("/employees")
            .retrieve()
            .bodyToFlux(Employee.class)
            .collectList()
            .onErrorReturn(List.of())
            .block();
    }
}
