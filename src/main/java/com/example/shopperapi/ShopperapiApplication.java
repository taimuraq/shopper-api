package com.example.shopperapi;

import com.example.shopperapi.config.ExternalServicesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ExternalServicesConfig.class)
@SpringBootApplication
public class ShopperapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopperapiApplication.class, args);
	}

}
