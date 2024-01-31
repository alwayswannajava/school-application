package db;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/db.properties";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER_NAME = "db.username";
    private static final String DATABASE_USER_PASSWORD = "db.password";
    private static Logger log = LogManager.getLogger(DatabaseConnector.class);
    private static final Properties properties = new Properties();

    static {
        readDatabaseFileProperties();
    }

    private static final void readDatabaseFileProperties() {
        log.trace("Creating database properties");
        try(InputStream inputStreamProperties = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(inputStreamProperties);
        }  catch (IOException e) {
           log.error("Something went wrong ", e);
        }
    }

    public Connection connectToDatabase() throws SQLException {
        String url = properties.getProperty(DATABASE_URL);
        String username = properties.getProperty(DATABASE_USER_NAME);
        String password = properties.getProperty(DATABASE_USER_PASSWORD);
        log.trace("Returning connection");
        return DriverManager.getConnection(url, username, password);
    }

}
