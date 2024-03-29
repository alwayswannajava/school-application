package com.spring.schoolApplication.service.serviceImpl;
import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.exception.*;
import com.spring.schoolApplication.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        List<Student> actualList = studentService.findStudentsByCourseName("History");
        verify(studentDao).findStudentsByCourseName("History");
        assertEquals(expectedList, actualList);
    }

    @DisplayName("Test correct create student")
    @Test
    void testCorrectCreatingStudent() {
        Student student = new Student(1, 3, "Ivan", "Mazepa");
        studentService.create(student);
        verify(studentDao, times(1)).create(student);
    }

    @DisplayName("Test throw StudentExistsException when create student")
    @Test
    void testThrowStudentExistsExceptionWhenCreateStudent(){
        Student student = new Student(2, 3, "Ivan", "Cirko");
        when(studentDao.isStudentExist(student.getStudentId())).thenReturn(true);
        assertThrows(StudentExistsException.class, () -> studentService.create(student));
    }

    @DisplayName("Test throw StudentIdIsLessThanZeroException when create student")
    @Test
    void testThrowStudentIdIsLessThanZeroExceptionWhenCreateStudent(){
        Student student = new Student(-50, 3, "Bogdan", "Khmelnitskiy");
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.create(student));
    }

    @DisplayName("Test delete student")
    @Test
    void testDeleteStudentById(){
        Student student = new Student(3, 2, "Ivan", "Ivanov");
        when(studentDao.isStudentExist(student.getStudentId())).thenReturn(true);
        studentService.deleteStudentById(student.getStudentId());
        verify(studentDao, times(1)).deleteStudentById(student.getStudentId());
    }


    @DisplayName("Test throw StudentDoesntExistException when delete student")
    @Test
    void testThrowStudentIdIsLessThanZeroExceptionWhenDeleteStudent(){
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.deleteStudentById(-500));
    }

    @DisplayName("Test throw StudentIdIsLessThanZeroException when delete student")
    @Test
    void testThrowStudentDoesntExistExceptionWhenDeleteStudent(){
        assertThrows(StudentDoesntExistException.class, () -> studentService.deleteStudentById(300));
    }

    @DisplayName("Test correct add student to course")
    @Test
    void testCorrectAddingStudentToCourse() {
        studentService.addStudentToCourse(1, 3);
        verify(studentDao).addStudentToCourse(1, 3);
    }

    @DisplayName("Test throw StudentIdIsLessThanZeroException when add student to course")
    @Test
    void testThrowStudentIdIsLessThanZeroExceptionWhenAddStudentToStudent(){
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.addStudentToCourse(-5, 3));
    }

    @DisplayName("Test throw CourseIdLessThanZeroException when add student to course")
    @Test
    void testThrowCourseIdLessThanZeroExceptionWhenAddStudentToStudent(){
        assertThrows(CourseIdLessThanZeroException.class, () -> studentService.addStudentToCourse(10, -100));
    }

    @DisplayName("Test throw StudentAlreadyAssignToCourseException when add student to course")
    @Test
    void testThrowStudentAlreadyAssignToCourseExceptionWhenAddStudentToStudent(){
        when(studentDao.isStudentAssignToCourse(1, 3)).thenThrow(StudentAlreadyAssignToCourseException.class);
        assertThrows(StudentAlreadyAssignToCourseException.class, () -> studentService.addStudentToCourse(1, 3));
    }

    @DisplayName("Test throw StudentIdIsLessThanZeroException when remove student from course")
    @Test
    void testThrowStudentIdIsLessThanZeroExceptionWhenRemoveStudentFromCourse(){
        assertThrows(StudentIdIsLessThanZeroException.class, () -> studentService.removeStudentFromCourse(-10, 5));
    }

    @DisplayName("Test throw CourseIdLessThanZeroException when remove student from course")
    @Test
    void testThrowCourseIdLessThanZeroExceptionWhenRemoveStudentFromCourse(){
        assertThrows(CourseIdLessThanZeroException.class, () -> studentService.removeStudentFromCourse(8, -35));
    }

    @DisplayName("Test throw StudentCourseDoesntExistException when remove student from course")
    @Test
    void testThrowStudentCourseDoesntExistExceptionWhenRemoveStudentFromCourse(){
        assertThrows(StudentCourseDoesntExistException.class, () -> studentService.removeStudentFromCourse(1000, 3000));
    }
}