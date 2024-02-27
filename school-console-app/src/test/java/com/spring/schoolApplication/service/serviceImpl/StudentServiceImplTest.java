package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.service.StudentService;
import com.spring.schoolApplication.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    @DisplayName("Test find students by course name")
    @Test
    void testCorrectFindingStudentsByCourseName() {

    }

    @DisplayName("Test correct create student")
    @Test
    void testCorrectCreatingStudent() {

    }

    @DisplayName("Test correct delete student")
    @Test
    void testCorrectDeletingStudentById() {

    }

    @DisplayName("Test correct add student to course")
    @Test
    void testCorrectAddingStudentToCourse() {

    }

    @DisplayName("Test correct remove student from course")
    @Test
    void testCorrectRemovingStudentFromCourse() {

    }

}