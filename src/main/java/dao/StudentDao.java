package dao;

import entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    List<Student> findStudentsByCourseName(String courseName);

    Student create(long studentId, long groupId, String firstName, String lastName);

    void deleteStudentById(long studentId);

    void addStudentToCourse(long studentId, long courseId);

    void removeStudentFromCourse(long studentId, long courseId);
}
