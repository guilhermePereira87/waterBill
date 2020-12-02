package br.edu.ifsc.waterbill;

/*
@author Guilherme Pereira
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class WaterbillApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterbillApiApplication.class, args); 
	}

}
