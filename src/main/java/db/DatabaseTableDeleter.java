package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTableDeleter {
    private static final String DROP_GROUPS_TABLE_QUERY = "DROP TABLE IF EXISTS groups cascade;";
    private static final String DROP_STUDENTS_TABLE_QUERY = "DROP TABLE IF EXISTS students cascade;";
    private static final String DROP_COURSES_TABLE_QUERY = "DROP TABLE IF EXISTS courses cascade;";
    private static final String DROP_STUDENTS_COURSES_TABLE_QUERY = "DROP TABLE IF EXISTS students_courses cascade;";

    public void dropDatabaseTables(Connection connection) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DROP_GROUPS_TABLE_QUERY);
            statement.executeUpdate(DROP_STUDENTS_TABLE_QUERY);
            statement.executeUpdate(DROP_COURSES_TABLE_QUERY);
            statement.executeUpdate(DROP_STUDENTS_COURSES_TABLE_QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
