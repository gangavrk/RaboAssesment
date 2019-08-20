package com.rabo.bootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author GangavRK
 *
 */
@SpringBootApplication(scanBasePackages = { "com.rabo" })
@EntityScan(basePackages = { "com.rabo.model" })
@EnableJpaRepositories(basePackages = { "com.rabo.repository" })
public class RaboBootApp {
	/**
	 * SprigBootApplication to load all available spring REST.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(RaboBootApp.class, args);
	}
}
