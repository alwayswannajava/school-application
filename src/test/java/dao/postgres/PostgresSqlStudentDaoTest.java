package dao.postgres;

import dao.CourseDao;
import dao.GroupDao;
import dao.StudentDao;
import db.DatabaseConnector;
import db.DatabaseTableCreator;
import db.DatabaseTableDeleter;
import entity.Course;
import entity.Group;
import entity.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostgresSqlStudentDaoTest {
    public static final DatabaseConnector connector = new DatabaseConnector();
    private static final String COUNT_STUDENTS_QUERY = "select count(*) from students;";
    private static final String COUNT_STUDENTS_COURSES_RECORDS_QUERY = "select count(*) from students_courses;";
    private static final String TRUNCATE_STUDENTS_TABLE_QUERY = "truncate table students cascade;";
    private static final String TRUNCATE_STUDENTS_COURSES_TABLE_QUERY = "truncate table students_courses cascade";

    @BeforeAll
    public static void setUp() throws SQLException {
        connector.readDatabaseFileProperties();
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        tableDeleter.dropDatabaseTables(connector.connectToDatabase());
        tableCreator.createDatabaseTables(connector.connectToDatabase());
        CourseDao courseDao = new PostgresSqlCourseDao();
        courseDao.create(new Course(1, "History", "History course"));
        GroupDao groupDao = new PostgresSqlGroupDao();
        groupDao.create(new Group(3, "XA-22"));
    }

    @DisplayName("Test finding students by course name")
    @Test
    public void testCorrectFindingStudentsByCourseName() {
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.create(new Student(3, "Ivan", "Ivanov"));
        studentDao.addStudentToCourse(1, 1);
        List<Student> expectedStudentsByCourseNameList = new ArrayList<>();
        expectedStudentsByCourseNameList.add(new Student(3, "Ivan", "Ivanov"));
        List<Student> actualStudentsByCourseNameList = studentDao.findStudentsByCourseName("History");
        try(Connection connection = connector.connectToDatabase();
            PreparedStatement deleteStatement = connection.prepareStatement(TRUNCATE_STUDENTS_TABLE_QUERY)) {
            deleteStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(expectedStudentsByCourseNameList, actualStudentsByCourseNameList);
    }

    @DisplayName("Test creating student")
    @Test
    public void testCorrectCreateStudent() {
        int countStudentsAfterCreating = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.create(new Student(3, "Ivan", "Ivanov"));
        try(Connection connection = connector.connectToDatabase();
            PreparedStatement preparedCountStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY)) {
            ResultSet resultSet = preparedCountStatement.executeQuery();
            resultSet.next();
            countStudentsAfterCreating = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterCreating);
    }

    @DisplayName("Test deleting student by id")
    @Test
    public void testCorrectDeletingStudentById() {
        int countStudentsAfterDeleting = 0;
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY)) {
            StudentDao studentDao = new PostgresSqlStudentDao();
            studentDao.deleteStudentById(1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterDeleting = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(0, countStudentsAfterDeleting);
    }

    @DisplayName("Test adding student to course")
    @Test
    public void testCorrectAddingStudentToCourse() {
        int countStudentsAfterAdding = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.create(new Student(3, "Ivan", "Ivanov"));
        studentDao.addStudentToCourse(1, 1);
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement deleteStatement = connection.prepareStatement(TRUNCATE_STUDENTS_COURSES_TABLE_QUERY);
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterAdding = resultSet.getInt(1);
            deleteStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterAdding);
    }

    @DisplayName("Test removing student from the course")
    @Test
    public void testCorrectRemovingStudentFromCourse() {
        int countStudentsAfterRemoving = 0;
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY)) {
            StudentDao studentDao = new PostgresSqlStudentDao();
            studentDao.create(new Student(3, "Ivan", "Ivanov"));
            studentDao.addStudentToCourse(1, 1);
            studentDao.removeStudentFromCourse(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterRemoving = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(0, countStudentsAfterRemoving);
    }
}