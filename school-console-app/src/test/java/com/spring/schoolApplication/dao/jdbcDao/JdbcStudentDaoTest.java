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
        scripts = {"/db/migration/V1__create_tables.sql", "/db/migration/V2__insert_data.sql"},
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
        List<Student> expectedFoundStudentsByCourseName = new ArrayList<>();
        expectedFoundStudentsByCourseName.add(new Student(1, 1, "Ivan", "Mazepa"));
        expectedFoundStudentsByCourseName.add(new Student(2, 2, "Ivan", "Golybev"));
        List<Student> actualFoundStudentsByCourseName = studentDao.findStudentsByCourseName("Literature");
        assertEquals(expectedFoundStudentsByCourseName, actualFoundStudentsByCourseName);
    }

    @DisplayName("Test correct create student")
    @Test
    void testCorrectCreatingStudent() {
        int countStudentsBeforeAdd = studentDao.countAllStudents();
        studentDao.create(new Student(7, 5, "Mykhailo", "Drapatyi"));
        int countStudentsAfterAdd = studentDao.countAllStudents();
        assertEquals(countStudentsBeforeAdd + 1, countStudentsAfterAdd);
    }

    @DisplayName("Test correct delete student")
    @Test
    void testCorrectDeletingStudentById() {
        int countStudentsBeforeDelete = studentDao.countAllStudents();
        studentDao.deleteStudentById(5);
        int countStudentsAfterDelete = studentDao.countAllStudents();
        assertEquals(countStudentsBeforeDelete - 1, countStudentsAfterDelete);
    }

    @DisplayName("Test correct add student to course")
    @Test
    void testCorrectAddingStudentToCourse() {
        int countStudentsCoursesBeforeAdd = studentDao.countAllStudentsCourses();
        studentDao.addStudentToCourse(6, 3);
        int countStudentsCoursesAfterAdd = studentDao.countAllStudentsCourses();
        assertEquals(countStudentsCoursesBeforeAdd + 1, countStudentsCoursesAfterAdd);
    }

    @DisplayName("Test correct remove student from course")
    @Test
    void testCorrectRemovingStudentFromCourse() {
        int countStudentsCoursesBeforeRemove = studentDao.countAllStudentsCourses();
        studentDao.removeStudentFromCourse(1, 3);
        int countStudentsCoursesAfterRemove = studentDao.countAllStudentsCourses();
        assertEquals(countStudentsCoursesBeforeRemove - 1, countStudentsCoursesAfterRemove);
    }
}