package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCourseDao implements CourseDao {
    private static final String COUNT_COURSE_EXISTS_BY_COURSE_ID_QUERY = "select count(*) from courses where course_id = ?";
    private static final String INSERT_TO_COURSES_TABLE_QUERY = "INSERT INTO courses (course_id, course_name, course_description) values (?, ?, ?);";
    private JdbcTemplate jdbcTemplate;

    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(Course course) {
        return jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, course.getCourseId(), course.getCourseName(), course.getCourseDescription());
    }

    @Override
    public boolean isCourseExist(long courseId) {
        Integer countExistCourse = jdbcTemplate.queryForObject(COUNT_COURSE_EXISTS_BY_COURSE_ID_QUERY, Integer.class, courseId);
        return countExistCourse != null && countExistCourse > 0;
    }

}
