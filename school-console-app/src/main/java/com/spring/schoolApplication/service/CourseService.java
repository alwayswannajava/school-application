package com.spring.schoolApplication.service;

import com.spring.schoolApplication.entity.Course;
import com.spring.schoolApplication.exception.CourseExistsException;
import org.springframework.stereotype.Service;

public interface CourseService {

    int create(Course course);


}
