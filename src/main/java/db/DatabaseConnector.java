package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DATABASE_NAME = "schoolDB";
    private static final String DATABASE_USER_NAME = "postgres";
    private static final String DATABASE_USER_PASSWORD = "root";

    public Connection connectToDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL + DATABASE_NAME,
                    DATABASE_USER_NAME, DATABASE_USER_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static String getDatabaseUrl() {
        return DATABASE_URL;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getDatabaseUserName() {
        return DATABASE_USER_NAME;
    }

    public static String getDatabaseUserPassword() {
        return DATABASE_USER_PASSWORD;
    }
}
