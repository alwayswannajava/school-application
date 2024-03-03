package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.exception.CourseExistsException;
import com.spring.schoolApplication.exception.CourseIdLessThanZeroException;
import com.spring.schoolApplication.service.CourseService;
import com.spring.schoolApplication.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseRepository;


    @Override
    public int create(Course course) {
        if (course.getCourseId() < 0) {
            throw new CourseIdLessThanZeroException("Cannot create course with id less than 0");
        }
        if (courseRepository.isCourseExist(course.getCourseId())) {
            throw new CourseExistsException("Course is already exist");
        }
        return courseRepository.create(course);
    }

}
