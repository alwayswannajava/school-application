package dao.postgres;

import dao.CourseDao;
import db.DatabaseConnector;
import entity.Course;
import entity.Student;
import mainClasses.DataGeneratorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PostgresSqlCourseDao implements CourseDao {
    private static Logger log = LogManager.getLogger(PostgresSqlCourseDao.class);
    DatabaseConnector connector = new DatabaseConnector();

    @Override
    public Course create(Course course) {
        log.info("Create new course: ");
        Course createdCourse = null;
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DataGeneratorUtil.INSERT_TO_COURSES_TABLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            log.trace("Opening connection, creating prepared statement");
            preparedStatement.setInt(1, course.getCourseId());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getCourseDescription());
            preparedStatement.execute();
            log.trace("Opening result set");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                log.trace("Get result set");
                resultSet.next();
                log.trace("Creating new course");
                createdCourse = new Course(resultSet.getInt("course_id"),
                        resultSet.getString("course_name"), resultSet.getString("course_description"));
                log.info("Course " + course.getCourseName() + " was succesfully created");
            }
        } catch (SQLException throwables) {
            log.error("Something went wrong", throwables);
        }
        log.trace("Closing connection, prepared statement, result set");
        return createdCourse;
    }
}

