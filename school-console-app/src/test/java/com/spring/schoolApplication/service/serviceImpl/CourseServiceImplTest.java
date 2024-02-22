package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.service.CourseService;
import com.spring.schoolApplication.entity.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;
import static org.junit.jupiter.api.Assertions.assertEquals;
class CourseServiceImplTest {


    @DisplayName("Test create course")
    @Test
    void testCorrectCreatingCourse() {

    }

    @DisplayName("Test find all courses")
    @Test
    void testCorrectCountAllCourses() {

    }
}