package com.spring.schoolApplication.dao;

import com.spring.schoolApplication.entity.Course;

import java.util.List;

public interface CourseDao {

    int create(Course course);

    int countAllCourses();

    boolean isCourseExist(long courseId);

}
