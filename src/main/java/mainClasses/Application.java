package mainClasses;

import dao.GroupDao;
import dao.StudentDao;
import dao.postgres.PostgresSqlGroupDao;
import dao.postgres.PostgresSqlStudentDao;
import db.DatabaseConnector;
import db.DatabaseTableCreator;
import db.DatabaseTableDeleter;
import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.connectToDatabase();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        tableDeleter.dropDatabaseTables(connection);
        tableCreator.createDatabaseTables(connection);
        DataGeneratorUtil dataGenerator = new DataGeneratorUtil();
        dataGenerator.generateGroups();
        dataGenerator.generateCourses();
        dataGenerator.generateStudents();
        dataGenerator.addGeneratedGroupsToDatabase(connection);
        dataGenerator.addGeneratedCoursesToDatabase(connection);
        dataGenerator.addGeneratedStudentsToDatabase(connection);
        dataGenerator.setRandomCoursesForStudents(connection);
        StudentDao studentDao = new PostgresSqlStudentDao();
        GroupDao groupDao = new PostgresSqlGroupDao();
        groupDao.findAllGroupByStudentId(50);
        studentDao.findStudentsByCourseName("Physics");
        studentDao.create(202, 2, "Ivan", "Ivanov");
        studentDao.addStudentToCourse(202, 5);
        studentDao.addStudentToCourse(202, 6);
        studentDao.removeStudentFromCourse(202, 5);
        QueryExecutor queryExecutor = new QueryExecutor();
        queryExecutor.executeQuery(connection);
    }
}
