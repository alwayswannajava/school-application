package mainClasses;

import entity.Course;
import entity.Group;
import entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorUtilTest {

    @DisplayName("Generating groups test")
    @Test
    public void testCorrectGeneratingGroups(){
        DataGeneratorUtil dataGenerator = new DataGeneratorUtil();
        Set<Group> generatedGroups = dataGenerator.generateGroups();
        assertEquals(11, generatedGroups.size());
    }

    @DisplayName("Generating courses test")
    @Test
    public void testCorrectGeneratingCourses(){
        DataGeneratorUtil dataGenerator = new DataGeneratorUtil();
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(1, "Math", "Math course"));
        expectedCourses.add(new Course(2, "Physical Education", "Physical education course"));
        expectedCourses.add(new Course(3, "Physics", "Physics course"));
        expectedCourses.add(new Course(4, "English", "English course"));
        expectedCourses.add(new Course(5, "History", "History course"));
        expectedCourses.add(new Course(6, "Information technology", "Information technology course"));
        expectedCourses.add(new Course(7, "Art", "Art course"));
        expectedCourses.add(new Course(8, "Geography", "Geography course"));
        expectedCourses.add(new Course(9, "Chemistry", "Chemistry course"));
        expectedCourses.add(new Course(10, "Literature", "Literature course"));
        List<Course> actualCourses = dataGenerator.generateCourses();
        assertEquals(expectedCourses, actualCourses);
    }


    @DisplayName("Generating students test")
    @Test
    public void testCorrectGeneratingStudents(){
        DataGeneratorUtil dataGenerator = new DataGeneratorUtil();
        Set<Student> generatedGroups = dataGenerator.generateStudents();
        assertEquals(200, generatedGroups.size());
    }

}