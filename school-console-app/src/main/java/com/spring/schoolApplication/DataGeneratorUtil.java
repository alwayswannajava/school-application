package com.spring.schoolApplication;

import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.entity.Student;
import com.spring.schoolApplication.entity.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DataGeneratorUtil {
    public static final String INSERT_TO_GROUP_TABLE_QUERY = "INSERT INTO groups (group_id, group_name) values (?, ?);";
    public static final String INSERT_TO_COURSES_TABLE_QUERY = "INSERT INTO courses (course_id, course_name, course_description) values (?, ?, ?);";
    public static final String INSERT_TO_STUDENTS_TABLE_QUERY = "INSERT INTO students (group_id, first_name, last_name) values (?, ?, ?)";
    public static final String INSERT_TO_STUDENTS_COURSES_TABLE_QUERY = "INSERT INTO students_courses (student_id, course_id) values (?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void addGeneratedGroupToDatabase(Group group) {
        jdbcTemplate.update(INSERT_TO_GROUP_TABLE_QUERY, 0, "Empty group");
        jdbcTemplate.update(INSERT_TO_GROUP_TABLE_QUERY, group.getGroupId(), group.getGroupName());

    }

    public void addGeneratedCoursesToDatabase() {
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 1, "Math", "Math course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 2, "Physical Education", "Physical education course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 3, "Physics", "Physics course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 4, "English", "English course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 5, "History", "History course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 6, "Information technology", "Information technology course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 7, "Art", "Art course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 8, "Geography", "Geography course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 9, "Chemistry", "Chemistry course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 10, "Literature", "Literature course");
    }

    public void addGeneratedStudentToDatabase(Student student) {
        jdbcTemplate.update(INSERT_TO_STUDENTS_TABLE_QUERY, student.getGroupId(), student.getFirstName(), student.getLastName());
    }

    public void setRandomCoursesForStudents(StudentCourse studentCourse) {
        jdbcTemplate.update(INSERT_TO_STUDENTS_COURSES_TABLE_QUERY, studentCourse.getStudentId() + 1, studentCourse.getCourseId());
    }
}



