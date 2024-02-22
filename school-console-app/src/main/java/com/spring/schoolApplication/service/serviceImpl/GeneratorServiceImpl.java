package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.DataGeneratorUtil;
import com.spring.schoolApplication.service.GeneratorService;
import org.springframework.stereotype.Service;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    private DataGeneratorUtil generatorRepository;

    public GeneratorServiceImpl(DataGeneratorUtil generatorRepository) {
        this.generatorRepository = generatorRepository;
    }

    @Override
    public void addGeneratedGroupsToDatabase() {
        generatorRepository.addGeneratedGroupsToDatabase();
    }

    @Override
    public void addGeneratedCoursesToDatabase() {
        generatorRepository.addGeneratedCoursesToDatabase();
    }

    @Override
    public void addGeneratedStudentsToDatabase() {
        generatorRepository.addGeneratedStudentsToDatabase();
    }

    @Override
    public void setRandomCoursesForStudents() {
        generatorRepository.setRandomCoursesForStudents();
    }
}
