package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/db/migration/V1__create_tables.sql", "/db/migration/V2__insert_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JdbcCourseDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CourseDao courseDao;

    @BeforeEach
    void setUp() {
        courseDao = new JdbcCourseDao(jdbcTemplate);
    }

    @DisplayName("Test create course")
    @Test
    void testCorrectCreatingCourse() {
        int countCoursesBeforeAdd = courseDao.countAllCourses();
        courseDao.create(new Course(11, "Basketball", "Basketball course"));
        int countCoursesAfterAdd = courseDao.countAllCourses();
        assertEquals(countCoursesBeforeAdd + 1, countCoursesAfterAdd);
    }

    @DisplayName("Test find all courses")
    @Test
    void testCorrectFindingAllCourses() {
        int expectedCountCourses = 10;
        int actualCountCourses = courseDao.countAllCourses();
        assertEquals(expectedCountCourses, actualCountCourses);
    }
}