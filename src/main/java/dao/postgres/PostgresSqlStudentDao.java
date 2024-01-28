package dao.postgres;

import dao.StudentDao;
import entity.Student;
import db.DatabaseConnector;
import mainClasses.DataGeneratorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgresSqlStudentDao implements StudentDao {
    private DatabaseConnector connector = new DatabaseConnector();
    public static final String FIND_STUDENTS_BY_COURSE_NAME_QUERY = "select s.student_id, s.group_id," +
            "s.first_name, s.last_name from students_courses\n" +
            "inner join courses c on c.course_id = students_courses.course_id\n" +
            "inner join students s on students_courses.student_id = s.student_id\n" +
            "where course_name = ?;";
    private static final String DROP_STUDENT_BY_ID_QUERY = "delete from students where student_id = ?;";
    private static final String DROP_STUDENT_BY_COURSE_QUERY = "delete from students_courses where student_id = ? and " +
            "course_id = ?;";

    private static Logger log = LogManager.getLogger(PostgresSqlStudentDao.class);

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        log.info("Find student by course name: " + courseName);
        List<Student> studentsByCourseNameList = new LinkedList<>();
        ResultSet resultSet;
        try(Connection connection = connector.connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENTS_BY_COURSE_NAME_QUERY)) {
            log.trace("Opening connection, creating prepared statement");
            preparedStatement.setString(1, courseName);
            log.trace("Opening result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                studentsByCourseNameList.add(new Student(groupId, firstName, lastName));
            }
        } catch (SQLException throwables) {
            log.error("Something went wrong", throwables);
        }
        log.trace("Closing connection, prepared statement, result set");
        return studentsByCourseNameList;
    }

    @Override
    public Student create(Student student) {
        log.info("Create new student: ");
        Student createdStudent = null;
        try(Connection connection = connector.connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_TABLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            log.trace("Opening connection, creating prepared statement");
            preparedStatement.setInt(1, student.getGroupId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.execute();
            log.trace("Opening result set");
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                log.trace("Get result set");
                resultSet.next();
                log.trace("Creating new student");
                createdStudent = new Student(resultSet.getInt("group_id"),
                        resultSet.getString("first_name"), resultSet.getString("last_name"));
                log.info("Student with name " + student.getFirstName() + " " + student.getLastName() + " was succesfully created");
            }
        } catch (SQLException throwables) {
            log.error("Something went wrong", throwables);
        }
        log.trace("Closing connection, prepared statement, result set");
        return createdStudent;
    }

    @Override
    public void deleteStudentById(long studentId) {
        log.info("Deleting student by id: " + studentId);
        try (Connection connection = connector.connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(DROP_STUDENT_BY_ID_QUERY)){
            log.trace("Opening connection, creating prepared statement");
            preparedStatement.setInt(1, (int) studentId);
            log.trace("Deleting student");
            preparedStatement.execute();
            log.info("Student with id " + studentId + " was succesfully deleted");
        } catch (SQLException throwables) {
            log.error("Something went wrong", throwables);
        }
        log.trace("Closing connection, prepared statement");
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        log.info("Adding student to course: ");
        try (Connection connection = connector.connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_STUDENTS_COURSES_TABLE_QUERY)){
            log.trace("Opening connection, creating prepared statement");
            preparedStatement.setInt(1, (int) studentId);
            preparedStatement.setInt(2, (int) courseId);
            log.trace("Adding student to course");
            preparedStatement.execute();
            log.info("Student with student_id " + studentId + " was successfully added to course " + courseId);
        } catch (SQLException throwables) {
            log.error("Something went wrong", throwables);
        }
        log.trace("Closing connection, prepared statement");
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
        log.info("Delete student from course: " + courseId);
        try(Connection connection = connector.connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(DROP_STUDENT_BY_COURSE_QUERY)){
            log.trace("Opening connection, creating prepared statement");
            preparedStatement.setInt(1, (int) studentId);
            preparedStatement.setInt(2, (int) courseId);
            log.trace("Deleting student from course");
            preparedStatement.execute();
            log.info("Student with student_id " + studentId + " was successfully removed from course " + courseId);
        } catch (SQLException throwables) {
            log.error("Something went wrong", throwables);
        }
        log.trace("Closing connection, prepared statement");
    }
}
