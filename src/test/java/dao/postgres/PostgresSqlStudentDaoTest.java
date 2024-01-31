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
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostgresSqlStudentDaoTest {
    private static DatabaseConnector connector = new DatabaseConnector();
    private static final String COUNT_STUDENTS_QUERY = "select count(*) from students;";
    private static final String COUNT_STUDENTS_COURSES_RECORDS_QUERY = "select count(*) from students_courses;";
    private static final String TEST_INSERT_TO_STUDENTS_TABLE_QUERY = "insert into students (student_id, group_id, first_name, last_name) " +
            "values (?, ?, ?, ?);";


    @BeforeAll
    public static void setUp() throws SQLException {
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
    public void testCorrectFindingStudentsByCourseName() {
        StudentDao studentDao = new PostgresSqlStudentDao();
        try(Connection connection = connector.connectToDatabase();
        PreparedStatement createStudentsStatement = connection.prepareStatement(TEST_INSERT_TO_STUDENTS_TABLE_QUERY)) {
            createStudentsStatement.setInt(1, 1);
            createStudentsStatement.setInt(2, 3);
            createStudentsStatement.setString(3, "Kostik");
            createStudentsStatement.setString(4, "Shevchenko");
            createStudentsStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        studentDao.addStudentToCourse(1, 1);
        List<Student> expectedStudentsByCourseNameList = new ArrayList<>();
        expectedStudentsByCourseNameList.add(new Student(3, "Kostik", "Shevchenko"));
        List<Student> actualStudentsByCourseNameList = studentDao.findStudentsByCourseName("History");
        assertEquals(expectedStudentsByCourseNameList, actualStudentsByCourseNameList);
        studentDao.deleteStudentById(1);
    }

    @DisplayName("Test creating student")
    @Test
    public void testCorrectCreateStudent() {
        int countStudentsAfterCreating = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedCountStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY)) {
            studentDao.create(new Student(3, "Petro", "Petrov"));
            ResultSet resultSet = preparedCountStatement.executeQuery();
            resultSet.next();
            countStudentsAfterCreating = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterCreating);
        studentDao.deleteStudentById(1);
    }

    @DisplayName("Test deleting student by id")
    @Test
    public void testCorrectDeletingStudentById() {
        int countStudentsAfterDeleting = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_QUERY);
             PreparedStatement createStudentsStatement = connection.prepareStatement(TEST_INSERT_TO_STUDENTS_TABLE_QUERY)) {
            createStudentsStatement.setInt(1, 1);
            createStudentsStatement.setInt(2, 3);
            createStudentsStatement.setString(3, "Bogdan");
            createStudentsStatement.setString(4, "Khmelnitskiy");
            createStudentsStatement.execute();
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
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY);
             PreparedStatement createStudentStatement = connection.prepareStatement(TEST_INSERT_TO_STUDENTS_TABLE_QUERY)) {
            createStudentStatement.setInt(1, 1);
            createStudentStatement.setInt(2, 3);
            createStudentStatement.setString(3, "Ivan");
            createStudentStatement.setString(4, "Golybev");
            createStudentStatement.execute();
            studentDao.addStudentToCourse(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterAdding = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, countStudentsAfterAdding);
        studentDao.deleteStudentById(1);
    }

    @DisplayName("Test removing student from the course")
    @Test
    public void testCorrectRemovingStudentFromCourse() {
        int countStudentsAfterRemoving = 0;
        StudentDao studentDao = new PostgresSqlStudentDao();
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_COURSES_RECORDS_QUERY);
             PreparedStatement createStudentStatement = connection.prepareStatement(TEST_INSERT_TO_STUDENTS_TABLE_QUERY)) {
            createStudentStatement.setInt(1, 1);
            createStudentStatement.setInt(2, 3);
            createStudentStatement.setString(3, "Dmytro");
            createStudentStatement.setString(4, "Mazepa");
            createStudentStatement.execute();
            studentDao.addStudentToCourse(1, 1);
            studentDao.removeStudentFromCourse(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countStudentsAfterRemoving = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(0, countStudentsAfterRemoving);
        studentDao.deleteStudentById(1);
    }
}