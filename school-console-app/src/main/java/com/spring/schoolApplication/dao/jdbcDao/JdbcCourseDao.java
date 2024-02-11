package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

@Repository
public class JdbcCourseDao implements CourseDao {
    private static final String CREATE_COURSE_QUERY = "insert into courses (course_id, course_name, course_description) values (?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(Course course) {
        return jdbcTemplate.update(CREATE_COURSE_QUERY, course.getCourseId(), course.getCourseName(), course.getCourseDescription());
    }


}