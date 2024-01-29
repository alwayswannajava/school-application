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
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostgresSqlStudentDaoTest {
    private static DatabaseConnector connector = new DatabaseConnector();
    private static final String COUNT_STUDENTS_QUERY = "select count(*) from students;";
    private static final String COUNT_STUDENTS_COURSES_RECORDS_QUERY = "select count(*) from students_courses;";

    @BeforeAll
    public static void setUp() throws SQLException {
        connector.readDatabaseFileProperties();
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        tableDeleter.dropDatabaseTables();
        tableCreator.createDatabaseTables();
        CourseDao courseDao = new PostgresSqlCourseDao();
        courseDao.create(new Course(1, "History", "History course"));
        GroupDao groupDao = new PostgresSqlGroupDao();
        groupDao.create(new Group(3, "XA-22"));
    }

    @DisplayName("Test finding students by course name")
    @Test
    @Order(1)
    public void testCorrectFindingStudentsByCourseName() {
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.create(new Student(3, "Ivan", "Ivanov"));
        studentDao.addStudentToCourse(1, 1);
        List<Student> expectedStudentsByCourseNameList = new ArrayList<>();
        expectedStudentsByCourseNameList.add(new Student(3, "Ivan", "Ivanov"));
        List<Student> actualStudentsByCourseNameList = studentDao.findStudentsByCourseName("History");
        assertEquals(expectedStudentsByCourseNameList, actualStudentsByCourseNameList);
        studentDao.deleteStudentById(1);
        expectedStudentsByCourseNameList.clear();
        actualStudentsByCourseNameList.clear();
    }

    @DisplayName("Test creating student")
    @Test
    @Order(2)
    public void testCorrectCreateStudent() {
        int countStudentsAfterCreating = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        studentDao.create(new Student(3, "Petro", "Petrov"));
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedCountStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY)) {
            ResultSet resultSet = preparedCountStatement.executeQuery();
            resultSet.next();
            countStudentsAfterCreating = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterCreating);
        studentDao.deleteStudentById(2);
    }

    @DisplayName("Test deleting student by id")
    @Test
    @Order(3)
    public void testCorrectDeletingStudentById() {
        int countStudentsAfterDeleting = 0;
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY)) {
            StudentDao studentDao = new PostgresSqlStudentDao();
            studentDao.create(new Student(3, "Bodgan", "Khmelnitskiy"));
            studentDao.deleteStudentById(3);
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
    @Order(4)
    public void testCorrectAddingStudentToCourse() {
        int countStudentsAfterAdding = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY);
             PreparedStatement createStudentStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_TABLE_QUERY)) {
            createStudentStatement.setInt(1, 3);
            createStudentStatement.setString(2, "Ivan");
            createStudentStatement.setString(3, "Golybev");
            createStudentStatement.execute();
            studentDao.addStudentToCourse(4, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterAdding = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterAdding);
        studentDao.deleteStudentById(4);
    }

    @DisplayName("Test removing student from the course")
    @Test
    @Order(5)
    public void testCorrectRemovingStudentFromCourse() {
        int countStudentsAfterRemoving = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY);
             PreparedStatement createStudentStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_TABLE_QUERY)) {
            createStudentStatement.setInt(1, 3);
            createStudentStatement.setString(2, "Dmytro");
            createStudentStatement.setString(3, "Mazepa");
            createStudentStatement.execute();
            studentDao.addStudentToCourse(5, 1);
            studentDao.removeStudentFromCourse(5, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterRemoving = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(0, countStudentsAfterRemoving);
        studentDao.deleteStudentById(5);
    }
}