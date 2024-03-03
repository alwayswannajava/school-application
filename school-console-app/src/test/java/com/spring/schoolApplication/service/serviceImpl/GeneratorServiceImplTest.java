package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.entity.Course;
import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GeneratorServiceImpl.class})
class GeneratorServiceImplTest {

    @MockBean
    private GroupDao groupDao;

    @MockBean
    private CourseDao courseDao;

    @MockBean
    private StudentDao studentDao;

    @Autowired
    private GeneratorServiceImpl generatorService;

    @DisplayName("Test generate groups")
    @Test
    void testGenerateGroups() {
        generatorService.generateGroups();
        verify(groupDao, times(10)).create(any(Group.class));
    }

    @DisplayName("Test generate courses")
    @Test
    void testGenerateCourses() {
        generatorService.generateCourses();
        verify(courseDao, times(10)).create(any(Course.class));
    }


    @DisplayName("Test generate students")
    @Test
    void testGenerateStudents() {
        generatorService.generateStudents();
        verify(studentDao, atLeast(100)).create(any(Student.class));
    }

    @DisplayName("Test set random courses for students")
    @Test
    void testSetRandomCoursesForStudents() {
        generatorService.setRandomCoursesForStudents();
        verify(studentDao, atLeast(200)).addStudentToCourse(any(Long.class), any(Long.class));
    }

    @DisplayName("Test generate db entities")
    @Test
    void testGenerateEntities() {
        generatorService.generateDbEntities();
        verify(groupDao, times(10)).create(any(Group.class));
        verify(studentDao, atLeast(100)).create(any(Student.class));
        verify(studentDao, atLeast(200)).addStudentToCourse(any(Long.class), any(Long.class));
    }
}