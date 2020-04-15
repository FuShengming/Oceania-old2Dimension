package com.old2dimension.OCEANIA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication//(exclude= {DataSourceAutoConfiguration.class})

public class OceaniaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OceaniaApplication.class, args);
    }
}
