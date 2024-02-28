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
        Course secondCourse = new Course(-5, "Basketball", "Basketball course");
        courseService.create(course);
        verify(courseDao).create(course);
        when(courseDao.create(course)).thenReturn(1);
        when(courseDao.isCourseExist(course.getCourseId())).thenThrow(CourseExistsException.class);
        when(courseDao.create(secondCourse)).thenThrow(CourseIdLessThanZeroException.class);
        assertThrows(CourseExistsException.class, () -> courseService.create(course));
        assertThrows(CourseIdLessThanZeroException.class, () -> courseService.create(secondCourse));
    }

}