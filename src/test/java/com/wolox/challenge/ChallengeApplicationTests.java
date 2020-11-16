package com.wolox.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ChallengeApplicationTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	void main() {
		ChallengeApplication.main(new String[]{});
		assertTrue(true);
	}

	@Test
	void contextLoads() throws Exception {
		this.mockMvc.perform(get("/actuator/health"))
				.andExpect(status().isOk());
	}

}
