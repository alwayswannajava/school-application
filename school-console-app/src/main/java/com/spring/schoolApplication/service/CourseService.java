package com.spring.schoolApplication.service;

import com.spring.schoolApplication.entity.Course;
import com.spring.schoolApplication.exception.CourseExistsException;

public interface CourseService {

    int create(Course course) throws CourseExistsException;

    int countAllCourses();

}
