package com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("!test")
public class EmployeeClientConfig {

    @Bean
    @ConditionalOnProperty(name = "external-services.personal-service.url")
    public EmployeeClient employeeClient(
            @Value("${external-services.personal-service.url}") String personalServiceUrl,
            WebClient.Builder webClientBuilder) {
        if (personalServiceUrl == null || personalServiceUrl.isBlank()) {
            return new NoOpEmployeeClient();
        }
        return new EmployeeClientImpl(personalServiceUrl, webClientBuilder);
    }

    @Bean
    @ConditionalOnMissingBean(EmployeeClient.class)
    public EmployeeClient noOpEmployeeClient() {
        return new NoOpEmployeeClient();
    }
}
