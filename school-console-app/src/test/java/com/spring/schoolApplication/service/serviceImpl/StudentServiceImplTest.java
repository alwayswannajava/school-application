package com.spring.schoolApplication.service.serviceImpl;
import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.exception.*;
import com.spring.schoolApplication.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {StudentServiceImpl.class})
class StudentServiceImplTest {

    @MockBean
    private StudentDao studentDao;

    @Autowired
    private StudentServiceImpl studentService;

    @DisplayName("Test find students by course name")
    @Test
    void testCorrectFindingStudentsByCourseName() {
        List<Student> expectedList = new ArrayList<>();
        studentService.findStudentsByCourseName("History");
        verify(studentDao).findStudentsByCourseName("History");
        when(studentDao.findStudentsByCourseName("History")).thenReturn(expectedList);
    }

    @DisplayName("Test correct create student")
    @Test
    void testCorrectCreatingStudent() {
        Student student = new Student(1, 3, "Ivan", "Mazepa");
        Student secondStudent = new Student(-50, 3, "Bogdan", "Khmelnitskiy");
        studentService.create(student);
        verify(studentDao).create(student);
        when(studentDao.create(student)).thenReturn(1);
        when(studentDao.isStudentExist(1)).thenThrow(StudentExistsException.class);
        when(studentDao.create(secondStudent)).thenThrow(StudentIdIsLessThanZeroException.class);
        assertThrows(StudentExistsException.class, () -> studentService.create(student));
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.create(secondStudent));
    }

    @DisplayName("Test correct delete student")
    @Test
    void testCorrectDeletingStudentById() {
        when(studentDao.deleteStudentById(10)).thenReturn(1);
        when(studentDao.deleteStudentById(-500)).thenThrow(StudentIdIsLessThanZeroException.class);
        when(studentDao.deleteStudentById(300)).thenThrow(StudentDoesntExistException.class);
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.deleteStudentById(-500));
        assertThrows(StudentDoesntExistException.class, () -> studentService.deleteStudentById(300));
    }

    @DisplayName("Test correct add student to course")
    @Test
    void testCorrectAddingStudentToCourse() {
        studentService.addStudentToCourse(1, 3);
        verify(studentDao).addStudentToCourse(1, 3);
        when(studentDao.addStudentToCourse(1, 3)).thenReturn(1);
        when(studentDao.addStudentToCourse(-5, 3)).thenThrow(StudentIdIsLessThanZeroException.class);
        when(studentDao.addStudentToCourse(10, -100)).thenThrow(CourseIdLessThanZeroException.class);
        when(studentDao.isStudentAssignToCourse(1, 3)).thenThrow(StudentAlreadyAssignToCourseException.class);
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.addStudentToCourse(-5, 3));
        assertThrows(CourseIdLessThanZeroException.class, () -> studentService.addStudentToCourse(10, -100));
        assertThrows(StudentAlreadyAssignToCourseException.class, () -> studentService.addStudentToCourse(1, 3));
    }

    @DisplayName("Test correct remove student from course")
    @Test
    void testCorrectRemovingStudentFromCourse() {
        when(studentDao.removeStudentFromCourse(5,8)).thenReturn(1);
        when(studentDao.removeStudentFromCourse(-10, 5)).thenThrow(StudentIdIsLessThanZeroException.class);
        when(studentDao.removeStudentFromCourse(8, -35)).thenThrow(CourseIdLessThanZeroException.class);
        when(!studentDao.isStudentAssignToCourse(5, 8)).thenThrow(StudentCourseDoesntExistException.class);
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.removeStudentFromCourse(-10, 5));
        assertThrows(CourseIdLessThanZeroException.class, () -> studentService.removeStudentFromCourse(8, -35));
        assertThrows(StudentCourseDoesntExistException.class, () -> studentService.removeStudentFromCourse(1000, 3000));
    }

}