package com.spring.schoolApplication.service;

import com.spring.schoolApplication.entity.Course;

public interface CourseService {

    int create(Course course);

    int countAllCourses();

}
