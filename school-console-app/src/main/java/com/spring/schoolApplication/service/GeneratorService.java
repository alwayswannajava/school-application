package com.spring.schoolApplication.service;

public interface GeneratorService {
    void addGeneratedGroupsToDatabase();

    void addGeneratedCoursesToDatabase();

    void addGeneratedStudentsToDatabase();

    void setRandomCoursesForStudents();

}
