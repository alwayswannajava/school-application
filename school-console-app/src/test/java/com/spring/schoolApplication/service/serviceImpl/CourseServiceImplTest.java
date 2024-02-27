package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.entity.Course;
import com.spring.schoolApplication.exception.CourseExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

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
        when(courseDao.create(any(Course.class))).thenReturn(1);
        when(courseDao.isCourseExist(1)).thenThrow(CourseExistsException.class);
        courseService.create(new Course(102, "Boxing", "Boxing course"));
        verify(courseDao.create(any(Course.class)));
    }


}