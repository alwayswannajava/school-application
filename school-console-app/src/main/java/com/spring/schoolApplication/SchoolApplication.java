package com.spring.schoolApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {

	}
}
