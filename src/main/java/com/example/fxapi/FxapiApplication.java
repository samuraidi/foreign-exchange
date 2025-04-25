package com.example.fxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.fxapi.client")
public class FxapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxapiApplication.class, args);
	}

}
