package com.spring.schoolApplication.service.serviceImpl;
import com.spring.schoolApplication.dao.jdbcDao.JdbcStudentDao;
import com.spring.schoolApplication.service.StudentService;
import com.spring.schoolApplication.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private JdbcStudentDao studentRepository;

    public StudentServiceImpl(JdbcStudentDao studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return studentRepository.findStudentsByCourseName(courseName);
    }

    @Override
    public int create(Student student) {
        return studentRepository.create(student);
    }

    @Override
    public int deleteStudentById(long studentId) {
        return studentRepository.deleteStudentById(studentId);
    }

    @Override
    public int addStudentToCourse(long studentId, long courseId) {
        return studentRepository.addStudentToCourse(studentId, courseId);
    }

    @Override
    public int removeStudentFromCourse(long studentId, long courseId) {
        return studentRepository.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public int countAllStudents() {
        return studentRepository.countAllStudents();
    }

    @Override
    public int countAllStudentsCourses() {
        return studentRepository.countAllStudentsCourses();
    }
}
