package com.ssafy.ssapilogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableMongoAuditing
public class SsapilogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsapilogueApplication.class, args);
	}

}
