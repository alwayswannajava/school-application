package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.exception.*;
import com.spring.schoolApplication.service.StudentService;
import com.spring.schoolApplication.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentRepository;

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return studentRepository.findStudentsByCourseName(courseName);
    }

    @Override
    public int create(Student student){
        if (student.getStudentId() < 0) {
            throw new StudentIdIsLessThanZeroException("Cannot create student with id " + student.getStudentId());
        } else if (studentRepository.isStudentExist(student.getStudentId())) {
            throw new StudentExistsException("Student is already exist");
        }
        return studentRepository.create(student);
    }

    @Override
    public int deleteStudentById(long studentId) {
        if (studentId < 0) {
            throw new StudentIdIsLessThanZeroException("Cannot delete student with id " + studentId);
        } else if (!studentRepository.isStudentExist(studentId)) {
            throw new StudentDoesntExistException("There is no student with id " + studentId);
        }
        return studentRepository.deleteStudentById(studentId);
    }

    @Override
    public int addStudentToCourse(long studentId, long courseId) {
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
    public int removeStudentFromCourse(long studentId, long courseId) {
        if (studentId < 0) {
            throw new StudentIdIsLessThanZeroException("Cannot remove student with id " + studentId);
        } else if (courseId < 0) {
            throw new CourseIdLessThanZeroException("Cannot remove course with id " + courseId);
        } else if (!studentRepository.isStudentAssignToCourse(studentId, courseId)) {
            throw new StudentCourseDoesntExistException("There are none student with id " + studentId + " and course with id " + courseId);
        }
        return studentRepository.removeStudentFromCourse(studentId, courseId);
    }
}
