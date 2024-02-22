package com.spring.schoolApplication.service;

import com.spring.schoolApplication.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findStudentsByCourseName(String courseName);

    int create(Student student);

    int deleteStudentById(long studentId);

    int addStudentToCourse(long studentId, long courseId);

    int removeStudentFromCourse(long studentId, long courseId);

    int countAllStudents();

    int countAllStudentsCourses();
}
