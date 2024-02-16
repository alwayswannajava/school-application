package com.spring.schoolApplication;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.dao.jdbcDao.JdbcCourseDao;
import com.spring.schoolApplication.dao.jdbcDao.JdbcGroupDao;
import com.spring.schoolApplication.dao.jdbcDao.JdbcStudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/db/migration/V1__create_tables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class DataGeneratorUtilTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private DataGeneratorUtil generator;
    private GroupDao groupDao;
    private CourseDao courseDao;
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        groupDao = new JdbcGroupDao(jdbcTemplate);
        courseDao = new JdbcCourseDao(jdbcTemplate);
        studentDao = new JdbcStudentDao(jdbcTemplate);
        generator = new DataGeneratorUtil(jdbcTemplate);
    }

    @DisplayName("Test generate groups")
    @Test
    void testCorrectAddGenerateGroupsToDatabase() {
        int expectedCountGroups = 11;
        generator.addGeneratedGroupsToDatabase();
        int actualCountGroups = groupDao.findAllGroups();
        assertEquals(expectedCountGroups, actualCountGroups);
    }

    @DisplayName("Test generate courses")
    @Test
    public void testCorrectGeneratingCourses() {
        int expectedCountCourses = 10;
        generator.addGeneratedCoursesToDatabase();
        int actualCountCourses = courseDao.findAllCourses();
        assertEquals(expectedCountCourses, actualCountCourses);
    }

    @DisplayName("Test generate students")
    @Test
    public void testCorrectGeneratingStudents() {
        int expectedCountStudents = 200;
        generator.addGeneratedGroupsToDatabase();
        generator.addGeneratedStudentsToDatabase();
        int actualCountStudents = studentDao.findAllStudents();
        assertEquals(expectedCountStudents, actualCountStudents);
    }
}