package com.ssafy.ssapilogue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsapilogueApplicationTests {

	static {
		System.setProperty("jasypt.encryptor.password", "ssapilogue104EShjDKkhHS");
	}

	@Test
	void contextLoads() {
	}

}
