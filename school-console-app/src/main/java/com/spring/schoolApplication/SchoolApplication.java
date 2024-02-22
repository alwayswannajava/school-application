package com.spring.schoolApplication;

import com.spring.schoolApplication.service.GeneratorService;
import com.spring.schoolApplication.service.serviceImpl.GeneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SchoolApplication implements ApplicationRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataGeneratorUtil generatorRepository;
    private DatabaseChecker checker;

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        checker = new DatabaseChecker(jdbcTemplate);
        if (checker.checkDatabaseOnEmpty() == 0) {
            GeneratorService generatorService = new GeneratorServiceImpl(generatorRepository);
            generatorService.addGeneratedCoursesToDatabase();
            generatorService.addGeneratedGroupsToDatabase();
            generatorService.addGeneratedStudentsToDatabase();
            generatorService.setRandomCoursesForStudents();
        }
    }
}
