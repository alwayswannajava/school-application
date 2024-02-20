package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.DataGeneratorUtil;
import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.entity.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

@Repository
public class JdbcCourseDao implements CourseDao {
    private static final String COUNT_ALL_COURSES_QUERY = "select count(*) from courses";

    private JdbcTemplate jdbcTemplate;

    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(Course course) {
        return jdbcTemplate.update(DataGeneratorUtil.INSERT_TO_COURSES_TABLE_QUERY, course.getCourseId(), course.getCourseName(), course.getCourseDescription());
    }

    @Override
    public int countAllCourses() {
        return jdbcTemplate.queryForObject(COUNT_ALL_COURSES_QUERY, Integer.class);
    }


}
