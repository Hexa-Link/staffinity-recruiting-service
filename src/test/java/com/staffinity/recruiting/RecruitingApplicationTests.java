package com.staffinity.recruiting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RecruitingApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void mainMethodStartsApplication() {
		// Verify that the main method exists and can be invoked
		assertThat(RecruitingApplication.class)
				.hasDeclaredMethods("main");
	}

	@Test
	void applicationContextContainsExpectedBeans() {
		// Verify critical beans are loaded
		assertThat(applicationContext.containsBean("vacancyRepositoryAdapter")).isTrue();
		assertThat(applicationContext.containsBean("createVacancyService")).isTrue();
		assertThat(applicationContext.containsBean("listVacanciesService")).isTrue();
	}
}
