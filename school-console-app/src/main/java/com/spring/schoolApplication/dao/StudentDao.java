package com.spring.schoolApplication.dao;

import com.spring.schoolApplication.entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> findStudentsByCourseName(String courseName);

    int create(Student student);

    int deleteStudentById(long studentId);

    int addStudentToCourse(long studentId, long courseId);

    int removeStudentFromCourse(long studentId, long courseId);

    List<Student> findAllStudents();
}
