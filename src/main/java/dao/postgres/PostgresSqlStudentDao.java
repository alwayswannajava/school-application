package dao.postgres;

import dao.StudentDao;
import entity.Student;
import db.DatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgresSqlStudentDao implements StudentDao {
    DatabaseConnector connector = new DatabaseConnector();
    private static final String FIND_STUDENTS_BY_COURSE_NAME_QUERY = "select s.student_id, s.group_id," +
            "s.first_name, s.last_name from students_courses\n" +
            "inner join courses c on c.course_id = students_courses.course_id\n" +
            "inner join students s on students_courses.student_id = s.student_id\n" +
            "where course_name = ?;";
    private static final String CREATE_NEW_STUDENT_QUERY = "insert into students (student_id, group_id, first_name, last_name) values (?, ?, ?, ?);";
    private static final String DROP_STUDENT_BY_ID_QUERY = "delete from students where student_id = ?;";
    private static final String ADD_STUDENT_TO_COURSE_QUERY = "insert into students_courses (student_id, course_id) values (?, ?);";
    private static final String DROP_STUDENT_BY_COURSE_QUERY = "delete from students_courses where student_id = ? and " +
            "course_id = ?;";

    private static Logger log = LogManager.getLogger(PostgresSqlStudentDao.class);

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        log.info("Find student by course name: " + courseName);
        List<Student> studentsByCourseNameList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Open connection");
            connection = connector.connectToDatabase();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(FIND_STUDENTS_BY_COURSE_NAME_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, courseName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int groupId = resultSet.getInt("group_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                studentsByCourseNameList.add(new Student(studentId, groupId, firstName, lastName));
            }
        } catch (SQLException throwables) {
            log.error("Cannot open connection", throwables);
        } finally {
            log.trace("Closing prepared statement");
            try {
                preparedStatement.close();
                log.trace("Prepared statement closed");
            } catch (SQLException throwables) {
                log.trace("Cannot close prepared statement", throwables);
            }
            log.trace("Closing result set");
            try {
                resultSet.close();
                log.trace("Result set closed");
            } catch (SQLException throwables) {
                log.error("Cannot close result set", throwables);
            }
            log.trace("Closing connection");
            try {
                connection.close();
                log.trace("Connection closed");
            } catch (SQLException throwables) {
                log.error("Cannot close connection", throwables);
            }
        }
        return studentsByCourseNameList;
    }

    @Override
    public Student create(long studentId, long groupId, String firstName, String lastName) {
        log.info("Create new student: ");
        Student student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Open connection");
            connection = connector.connectToDatabase();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(CREATE_NEW_STUDENT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) studentId);
            preparedStatement.setInt(2, (int) groupId);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.execute();
            try {
                log.trace("Get result set");
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                log.trace("Creating new student");
                student = new Student(resultSet.getInt("student_id"), resultSet.getInt("group_id"),
                        resultSet.getString("first_name"), resultSet.getString("last_name"));
                log.info("Student with name " + firstName + " " + lastName + " was succesfully created");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } catch (SQLException throwables) {
            log.error("Cannot open connection", throwables);
        } finally {
            log.trace("Closing prepared statement");
            try {
                preparedStatement.close();
                log.trace("Prepared statement closed");
            } catch (SQLException throwables) {
                log.trace("Cannot close prepared statement", throwables);
            }
            log.trace("Closing result set");
            try {
                resultSet.close();
                log.trace("Result set closed");
            } catch (SQLException throwables) {
                log.error("Cannot close result set", throwables);
            }
            log.trace("Closing connection");
            try {
                connection.close();
                log.trace("Connection closed");
            } catch (SQLException throwables) {
                log.error("Cannot close connection", throwables);
            }
        }
        return student;
    }

    @Override
    public void deleteStudentById(long studentId) {
        log.info("Delete student by id: " + studentId);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Open connection");
            connection = connector.connectToDatabase();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(DROP_STUDENT_BY_ID_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) studentId);
            log.trace("Deleting student");
            preparedStatement.execute();
            log.info("Student with id " + studentId + " was succesfully deleted");
        } catch (SQLException throwables) {
            log.error("Cannot open connection", throwables);
        } finally {
            log.trace("Closing prepared statement");
            try {
                preparedStatement.close();
                log.trace("Prepared statement closed");
            } catch (SQLException throwables) {
                log.trace("Cannot close prepared statement", throwables);
            }
            log.trace("Closing connection");
            try {
                connection.close();
                log.trace("Connection closed");
            } catch (SQLException throwables) {
                log.error("Cannot close connection", throwables);
            }
        }
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        log.info("Adding student to course: ");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Open connection");
            connection = connector.connectToDatabase();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(ADD_STUDENT_TO_COURSE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) studentId);
            preparedStatement.setInt(2, (int) courseId);
            log.trace("Adding student to course");
            preparedStatement.execute();
            log.info("Student with student_id " + studentId + " was successfully added to course " + courseId);
        } catch (SQLException throwables) {
            log.error("Cannot open connection", throwables);
        } finally {
            log.trace("Closing prepared statement");
            try {
                preparedStatement.close();
                log.trace("Prepared statement closed");
            } catch (SQLException throwables) {
                log.trace("Cannot close prepared statement", throwables);
            }
            log.trace("Closing connection");
            try {
                connection.close();
                log.trace("Connection closed");
            } catch (SQLException throwables) {
                log.error("Cannot close connection", throwables);
            }
        }
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
        log.info("Delete student from course: " + courseId);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Open connection");
            connection = connector.connectToDatabase();
            log.trace("Create prepared statement");
            preparedStatement = connection.prepareStatement(DROP_STUDENT_BY_COURSE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) studentId);
            preparedStatement.setInt(2, (int) courseId);
            log.trace("Deleting student from course");
            preparedStatement.execute();
            log.info("Student with student_id " + studentId + " was successfully removed from course " + courseId);
        } catch (SQLException throwables) {
            log.error("Cannot open connection", throwables);
        } finally {
            log.trace("Closing prepared statement");
            try {
                preparedStatement.close();
                log.trace("Prepared statement closed");
            } catch (SQLException throwables) {
                log.trace("Cannot close prepared statement", throwables);
            }
            log.trace("Closing connection");
            try {
                connection.close();
                log.trace("Connection closed");
            } catch (SQLException throwables) {
                log.error("Cannot close connection", throwables);
            }
        }
    }
}
