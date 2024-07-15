package org.dafe.tripTix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NiitFestacApplication {

	public static void main(String[] args) {
		SpringApplication.run(NiitFestacApplication.class, args);
	}

}
