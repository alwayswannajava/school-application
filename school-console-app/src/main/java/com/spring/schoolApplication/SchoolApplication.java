package com.spring.schoolApplication;

import com.spring.schoolApplication.service.serviceImpl.GeneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SchoolApplication implements ApplicationRunner {

    @Autowired
    private DatabaseChecker checker;

    @Autowired
    private GeneratorServiceImpl generatorService;


    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(!checker.checkDatabaseOnEmpty()){
            generatorService.generateDbEntities();
        }

    }
}
