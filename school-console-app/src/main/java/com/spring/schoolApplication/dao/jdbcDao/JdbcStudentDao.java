package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcStudentDao implements StudentDao {
    public static final String FIND_STUDENTS_BY_COURSE_NAME_QUERY = "select s.student_id, s.group_id," +
            "s.first_name, s.last_name from students_courses\n" +
            "inner join courses c on c.course_id = students_courses.course_id\n" +
            "inner join students s on students_courses.student_id = s.student_id\n" +
            "where course_name = ?;";
    private static final String DROP_STUDENT_BY_ID_QUERY = "delete from students where student_id = ?;";
    private static final String DROP_STUDENT_BY_COURSE_QUERY = "delete from students_courses where student_id = ? and " +
            "course_id = ?;";
    private static final String ADD_STUDENT_TO_COURSE_QUERY = "insert into students_courses (student_id, course_id) values (?, ?);";
    private static final String CREATE_STUDENT_QUERY = "insert into students (student_id, group_id, first_name, last_name) values (?, ?, ?, ?);";
    private static final String FIND_ALL_STUDENTS_QUERY = "select * from students;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return jdbcTemplate.query(FIND_STUDENTS_BY_COURSE_NAME_QUERY, BeanPropertyRowMapper.newInstance(Student.class), courseName);
    }

    @Override
    public int create(Student student) {
        return jdbcTemplate.update(CREATE_STUDENT_QUERY, student.getStudentId(), student.getGroupId(), student.getFirstName(), student.getLastName());
    }

    @Override
    public int deleteStudentById(long studentId) {
        return jdbcTemplate.update(DROP_STUDENT_BY_ID_QUERY, studentId);
    }

    @Override
    public int addStudentToCourse(long studentId, long courseId) {
        return jdbcTemplate.update(ADD_STUDENT_TO_COURSE_QUERY, studentId, courseId);
    }

    @Override
    public int removeStudentFromCourse(long studentId, long courseId) {
        return jdbcTemplate.update(DROP_STUDENT_BY_COURSE_QUERY, studentId, courseId);
    }

    @Override
    public List<Student> findAllStudents() {
        return jdbcTemplate.query(FIND_ALL_STUDENTS_QUERY, BeanPropertyRowMapper.newInstance(Student.class));
    }

}
