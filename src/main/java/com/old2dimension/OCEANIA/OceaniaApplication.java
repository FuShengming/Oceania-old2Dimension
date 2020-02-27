package com.old2dimension.OCEANIA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication//(exclude= {DataSourceAutoConfiguration.class})
public class OceaniaApplication {
	public static void main(String[] args) {
		SpringApplication.run(OceaniaApplication.class, args);
	}
}
