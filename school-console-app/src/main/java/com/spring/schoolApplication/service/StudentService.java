package com.spring.schoolApplication.service;

import com.spring.schoolApplication.entity.Student;
import com.spring.schoolApplication.exception.*;

import java.util.List;

public interface StudentService {
    List<Student> findStudentsByCourseName(String courseName);

    int create(Student student) throws StudentExistsException, StudentIdIsLessThanZeroException;

    int deleteStudentById(long studentId) throws StudentDoesntExistException, StudentIdIsLessThanZeroException;

    int addStudentToCourse(long studentId, long courseId) throws StudentAlreadyAssignToCourseException, StudentIdIsLessThanZeroException, CourseIdLessThanZeroException;

    int removeStudentFromCourse(long studentId, long courseId) throws StudentIdIsLessThanZeroException, CourseIdLessThanZeroException, StudentCourseDoesntExistException;

    int countAllStudents();

    int countAllStudentsCourses();
}
