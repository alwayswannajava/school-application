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
        databaseConnector.readDatabaseFileProperties();
        Connection connection = databaseConnector.connectToDatabase();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        tableDeleter.dropDatabaseTables();
        tableCreator.createDatabaseTables();
        DataGeneratorUtil dataGenerator = new DataGeneratorUtil();
        dataGenerator.generateGroups();
        dataGenerator.generateCourses();
        dataGenerator.generateStudents();
        dataGenerator.addGeneratedGroupsToDatabase();
        dataGenerator.addGeneratedCoursesToDatabase();
        dataGenerator.addGeneratedStudentsToDatabase();
        dataGenerator.setRandomCoursesForStudents();
        StudentDao studentDao = new PostgresSqlStudentDao();
        GroupDao groupDao = new PostgresSqlGroupDao();
        groupDao.findAllGroupByStudentId(50);
        studentDao.findStudentsByCourseName("History");
        Student student = new Student(2, "Ivan", "Ivanov");
        studentDao.create(student);
        studentDao.addStudentToCourse(2, 8);
        studentDao.removeStudentFromCourse(2, 8);
        QueryExecutor queryExecutor = new QueryExecutor();
        queryExecutor.executeQuery();
    }
}
