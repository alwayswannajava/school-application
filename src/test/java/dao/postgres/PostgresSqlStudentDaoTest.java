package dao.postgres;

import dao.StudentDao;
import db.DatabaseConnector;
import db.DatabaseTableCreator;
import db.DatabaseTableDeleter;
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
    public static final DatabaseConnector connector = new DatabaseConnector();
    private static final String COUNT_STUDENTS_QUERY = "select count(*) from students;";
    private static final String COUNT_STUDENTS_COURSES_RECORDS_QUERY = "select count(*) from students_courses;";


    @BeforeAll
    public static void setUp() throws SQLException {
        connector.readDatabaseFileProperties();
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        tableDeleter.dropDatabaseTables(connector.connectToDatabase());
        tableCreator.createDatabaseTables(connector.connectToDatabase());
    }

    @DisplayName("Test creating student")
    @Test
    public void testCorrectCreateStudent() {
        int countStudentsAfterCreating = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.create(new Student(3, "Petya", "Petrov"));
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterCreating = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(2, countStudentsAfterCreating);
    }

    @DisplayName("Test deleting student by id")
    @Test
    public void testCorrectDeletingStudentById() {
        int countStudentsAfterDeleting = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.deleteStudentById(2);
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterDeleting = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterDeleting);
    }

    @DisplayName("Test adding student to course")
    @Test
    public void testCorrectAddingStudentToCourse() {
        int countStudentsAfterAdding = 0;
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_GROUP_TABLE_QUERY)) {
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "AA-22");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_COURSES_TABLE_QUERY)) {
            preparedStatement.setInt(1, 5);
            preparedStatement.setString(2, "History");
            preparedStatement.setString(3, "History course");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.create(new Student(3, "aaa", "aaaaa"));
        studentDao.addStudentToCourse(1, 5);
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterAdding = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterAdding);
    }

    @DisplayName("Test removing student from the course")
    @Test
    public void testCorrectRemovingStudentFromCourse() {
        int countStudentsAfterRemoving = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.removeStudentFromCourse(1, 5);
        studentDao.removeStudentFromCourse(1, 6);
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterRemoving = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(0, countStudentsAfterRemoving);
    }
}