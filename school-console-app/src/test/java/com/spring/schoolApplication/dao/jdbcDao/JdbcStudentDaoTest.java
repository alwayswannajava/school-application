package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.StudentDao;
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

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/V1__create_test_tables.sql", "/sql/V2__insert_data_to_test_tables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JdbcStudentDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new JdbcStudentDao(jdbcTemplate);
    }

    @DisplayName("Test find students by course name")
    @Test
    void testCorrectFindingStudentsByCourseName() {
        studentDao.addStudentToCourse(1, 3);
        studentDao.addStudentToCourse(2, 3);
        List<Student> expectedFoundStudentsByCourseName = new ArrayList<>();
        expectedFoundStudentsByCourseName.add(new Student(1, 1, "Ivan", "Mazepa"));
        expectedFoundStudentsByCourseName.add(new Student(2, 2, "Ivan", "Golybev"));
        List<Student> actualFoundStudentsByCourseName = studentDao.findStudentsByCourseName("Literature");
        assertEquals(expectedFoundStudentsByCourseName, actualFoundStudentsByCourseName);
    }

    @DisplayName("Test correct create student")
    @Test
    void testCorrectCreatingStudent() {
        int expectedReturnAfterAdd = studentDao.create(new Student(7, 5, "Mykhailo", "Drapatyi"));
        assertEquals(1, expectedReturnAfterAdd);
    }

    @DisplayName("Test correct delete student")
    @Test
    void testCorrectDeletingStudentById() {
        int expectedReturnAfterDelete = studentDao.deleteStudentById(4);
        assertEquals(1, expectedReturnAfterDelete);
    }

    @DisplayName("Test correct add student to course")
    @Test
    void testCorrectAddingStudentToCourse() {
        int expectedReturnAfterAddStudentToCourse = studentDao.addStudentToCourse(1, 3);
        assertEquals(1, expectedReturnAfterAddStudentToCourse);
    }

    @DisplayName("Test correct remove student from course")
    @Test
    void testCorrectRemovingStudentFromCourse() {
        studentDao.addStudentToCourse(1, 3);
        int expectedReturnAfterRemoveStudentFromCourse = studentDao.removeStudentFromCourse(1, 3);
        assertEquals(1, expectedReturnAfterRemoveStudentFromCourse);
    }
}