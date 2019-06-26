package com.br.ccs.mark.version.on.ccsmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CcsmarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcsmarkApplication.class, args);
	}

}