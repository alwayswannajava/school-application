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
import mainClasses.DataGeneratorUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostgresSqlStudentDaoTest {
    public static DatabaseConnector connector = new DatabaseConnector();
    private static final String COUNT_STUDENTS_QUERY = "select count(*) from students;";
    private static final String COUNT_STUDENTS_COURSES_RECORDS_QUERY = "select count(*) from students_courses;";


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
        studentDao.create(new Student(3, "Petya", "Petrov"));
        studentDao.addStudentToCourse(2, 1);
        List<Student> expectedStudentsByCourseNameList = new ArrayList<>();
        expectedStudentsByCourseNameList.add(new Student(3, "Petya", "Petrov"));
        List<Student> actualStudentsByCourseNameList = studentDao.findStudentsByCourseName("History");
        try(Connection connection = connector.connectToDatabase();
            PreparedStatement preparedStatement = connection.prepareStatement("truncate table students cascade;")) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(expectedStudentsByCourseNameList, actualStudentsByCourseNameList);
    }

    @DisplayName("Test creating student")
    @Test
    public void testCorrectCreateStudent() {
        int countStudentsAfterCreating = 0;
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedCountStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY);
             PreparedStatement preparedCreateStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_TABLE_QUERY)) {
            connection.setAutoCommit(false);
            preparedCreateStatement.setInt(1, 3);
            preparedCreateStatement.setString(2, "Ivan");
            preparedCreateStatement.setString(3, "Ivanov");
            preparedCreateStatement.execute();
            ResultSet resultSet = preparedCountStatement.executeQuery();
            connection.commit();
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
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY);
             PreparedStatement preparedCreateStudentCourseStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_COURSES_TABLE_QUERY);
             PreparedStatement preparedCreateStudentStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_TABLE_QUERY)) {
            connection.setAutoCommit(false);
            preparedCreateStudentStatement.setInt(1, 3);
            preparedCreateStudentStatement.setString(2, "Ivan");
            preparedCreateStudentStatement.setString(3, "Ivanov");
            preparedCreateStudentStatement.execute();
            preparedCreateStudentCourseStatement.setInt(1, 1);
            preparedCreateStudentCourseStatement.setInt(2, 1);
            preparedCreateStudentCourseStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterAdding = resultSet.getInt(1);
            connection.rollback();
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
            studentDao.removeStudentFromCourse(1, 5);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterRemoving = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(0, countStudentsAfterRemoving);
    }
}