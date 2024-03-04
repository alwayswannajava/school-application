package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.entity.Course;
import com.spring.schoolApplication.exception.CourseExistsException;
import com.spring.schoolApplication.exception.CourseIdLessThanZeroException;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@SpringBootTest(classes = {CourseServiceImpl.class})
class CourseServiceImplTest {

    @MockBean
    private CourseDao courseDao;

    @Autowired
    private CourseServiceImpl courseService;


    @DisplayName("Test create course")
    @Test
    void testCorrectCreatingCourse() {
        Course course = new Course(10, "Boxing", "Boxing course");
        courseService.create(course);
        verify(courseDao, times(1)).create(course);
    }

    @DisplayName("Test throw CourseIdLessThanZeroException when create course")
    @Test
    void testThrowCourseIdLessThanZeroExceptionWhenCreateCourse() {
        Course course = new Course(-5, "Basketball", "Basketball course");;
        assertThrows(CourseIdLessThanZeroException.class, () -> courseService.create(course));
    }

    @DisplayName("Test isGroupExist method")
    @Test
    void testIsGroupExistMethodReturnsBoolean(){
        Course course = new Course(12, "History", "History");
        courseDao.isCourseExist(course.getCourseId());
        verify(courseDao).isCourseExist(course.getCourseId());
    }

}