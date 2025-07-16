package com.kairosLens;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class KairoslensApplicationTests {

	@Test
	void contextLoads() {
		//Minimal startup test to ensure the application context loads correctly
		//This test does not require a web environment, so we use NONE to avoid starting
		//the embedded server, which is not needed for this test.
	}

}
