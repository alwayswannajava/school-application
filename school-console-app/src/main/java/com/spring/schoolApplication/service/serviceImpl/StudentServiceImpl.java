package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.jdbcDao.JdbcStudentDao;
import com.spring.schoolApplication.exception.*;
import com.spring.schoolApplication.service.StudentService;
import com.spring.schoolApplication.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private JdbcStudentDao studentRepository;

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return studentRepository.findStudentsByCourseName(courseName);
    }

    @Override
    public int create(Student student) throws StudentIdIsLessThanZeroException, StudentExistsException {
        if (student.getStudentId() < 0) {
            throw new StudentIdIsLessThanZeroException("Cannot create student with id " + student.getStudentId());
        } else if (studentRepository.isStudentExist(student.getStudentId())) {
            throw new StudentExistsException("Student is already exist");
        }
        return studentRepository.create(student);
    }

    @Override
    public int deleteStudentById(long studentId) throws StudentDoesntExistException, StudentIdIsLessThanZeroException {
        if (studentId < 0) {
            throw new StudentIdIsLessThanZeroException("Cannot create student with id " + studentId);
        } else if (!studentRepository.isStudentExist(studentId)) {
            throw new StudentDoesntExistException("Cannot delete student by id " + studentId + ". Maybe it has deleted before");
        }
        return studentRepository.deleteStudentById(studentId);
    }

    @Override
    public int addStudentToCourse(long studentId, long courseId) throws StudentIdIsLessThanZeroException, CourseIdLessThanZeroException, StudentAlreadyAssignToCourseException {
        if (studentId < 0) {
            throw new StudentIdIsLessThanZeroException("Cannot assign student with id " + studentId);
        } else if (courseId < 0) {
            throw new CourseIdLessThanZeroException("Cannot assign course with id " + courseId);
        } else if(studentRepository.isStudentAssignToCourse(studentId, courseId)){
            throw new StudentAlreadyAssignToCourseException("Student has already assign to this course before ");
        }
        return studentRepository.addStudentToCourse(studentId, courseId);
    }

    @Override
    public int removeStudentFromCourse(long studentId, long courseId) throws StudentIdIsLessThanZeroException, CourseIdLessThanZeroException, StudentCourseDoesntExistException {
        if (studentId < 0) {
            throw new StudentIdIsLessThanZeroException("Cannot remove student with id " + studentId);
        } else if (courseId < 0) {
            throw new CourseIdLessThanZeroException("Cannot remove course with id " + courseId);
        } else if (!studentRepository.isStudentAssignToCourse(studentId, courseId)) {
            throw new StudentCourseDoesntExistException("There are none student with id " + studentId + " and course with id " + courseId);
        }
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
