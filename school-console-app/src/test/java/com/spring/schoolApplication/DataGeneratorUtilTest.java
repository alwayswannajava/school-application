package com.spring.schoolApplication;

import com.spring.schoolApplication.entity.Course;
import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorUtilTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private DataGeneratorUtil generator;


    @BeforeEach
    void setUp() {
        generator = new DataGeneratorUtil(jdbcTemplate);
    }

    @DisplayName("Test generate groups")
    @Test
    void testCorrectGenerateGroups() {
        Set<Group> generatedGroups = generator.generateGroups();
        assertEquals(11, generatedGroups.size());
    }

    @DisplayName("Test generate courses")
    @Test
    public void testCorrectGeneratingCourses() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(1, "Math", "Math course"));
        expectedCourses.add(new Course(2, "Physical Education", "Physical education course"));
        expectedCourses.add(new Course(3, "Physics", "Physics course"));
        expectedCourses.add(new Course(4, "English", "English course"));
        expectedCourses.add(new Course(5, "History", "History course"));
        expectedCourses.add(new Course(6, "Information technology", "Information technology course"));
        expectedCourses.add(new Course(7, "Art", "Art course"));
        expectedCourses.add(new Course(8, "Geography", "Geography course"));
        expectedCourses.add(new Course(9, "Chemistry", "Chemistry course"));
        expectedCourses.add(new Course(10, "Literature", "Literature course"));
        List<Course> actualCourses = generator.generateCourses();
        assertEquals(expectedCourses, actualCourses);
    }
    @DisplayName("Test generate students")
    @Test
    public void testCorrectGeneratingStudents() {
        Set<Student> generatedGroups = generator.generateStudents();
        assertEquals(200, generatedGroups.size());
    }
}