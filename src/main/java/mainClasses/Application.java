package mainClasses;

import dao.GroupDao;
import dao.StudentDao;
import dao.postgres.PostgresSqlGroupDao;
import dao.postgres.PostgresSqlStudentDao;
import db.DatabaseConnector;
import db.DatabaseTableCreator;
import db.DatabaseTableDeleter;
import entity.Student;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
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
        Student student = new Student(2, "Ivan", "Ivanov");
        studentDao.create(student);
        studentDao.addStudentToCourse(2, 5);
        studentDao.addStudentToCourse(2, 6);
        studentDao.removeStudentFromCourse(2, 5);
        QueryExecutor queryExecutor = new QueryExecutor();
        queryExecutor.executeQuery(connection);
    }
}
