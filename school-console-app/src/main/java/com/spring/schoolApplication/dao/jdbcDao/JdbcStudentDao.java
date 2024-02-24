package com.spring.schoolApplication.dao.jdbcDao;

import com.spring.schoolApplication.DataGeneratorUtil;
import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.entity.Student;
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
    private static final String CREATE_STUDENT_QUERY = "insert into students (student_id, group_id, first_name, last_name) values (?, ?, ?, ?);";
    private static final String COUNT_ALL_STUDENTS_QUERY = "select count(*) from students;";
    private static final String COUNT_ALL_STUDENTS_COURSES_QUERY = "select count(*) from students_courses";
    private static final String COUNT_STUDENTS_BY_STUDENT_ID_QUERY = "select count(*) from students where student_id = ?";
    private static final String COUNT_STUDENTS_ASSIGN_TO_COURSE_BY_STUDENT_ID_AND_COURSE_ID_QUERY = "select count(*) from students_courses where " +
            "student_id = ? " +
            "and course_id = ?";

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
        return jdbcTemplate.update(DataGeneratorUtil.INSERT_TO_STUDENTS_COURSES_TABLE_QUERY, studentId, courseId);
    }

    @Override
    public int removeStudentFromCourse(long studentId, long courseId) {
        return jdbcTemplate.update(DROP_STUDENT_BY_COURSE_QUERY, studentId, courseId);
    }

    @Override
    public int countAllStudents() {
        return jdbcTemplate.queryForObject(COUNT_ALL_STUDENTS_QUERY, Integer.class);
    }

    @Override
    public int countAllStudentsCourses() {
        return jdbcTemplate.queryForObject(COUNT_ALL_STUDENTS_COURSES_QUERY, Integer.class);
    }

    @Override
    public boolean isStudentExist(long studentId) {
        Integer countExistStudent = jdbcTemplate.queryForObject(COUNT_STUDENTS_BY_STUDENT_ID_QUERY, Integer.class, studentId);
        return countExistStudent != null && countExistStudent > 0;
    }

    @Override
    public boolean isStudentAssignToCourse(long studentId, long courseId) {
        Integer countExistStudentAssignToCourse = jdbcTemplate.queryForObject(COUNT_STUDENTS_ASSIGN_TO_COURSE_BY_STUDENT_ID_AND_COURSE_ID_QUERY,
                Integer.class, studentId, courseId);
        return countExistStudentAssignToCourse != null && countExistStudentAssignToCourse > 0;
    }

}
