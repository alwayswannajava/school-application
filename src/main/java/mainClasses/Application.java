package mainClasses;

import dao.GroupDao;
import dao.StudentDao;
import dao.postgres.PostgresSqlGroupDao;
import dao.postgres.PostgresSqlStudentDao;
import db.DatabaseConnector;
import db.DatabaseTableCreator;
import db.DatabaseTableDeleter;
import entity.Course;
import entity.Group;
import entity.Student;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class Application {
    public static void main(String[] args) throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.readDatabaseFileProperties();
        Connection connection = databaseConnector.connectToDatabase();
        DatabaseTableCreator tableCreator = new DatabaseTableCreator();
        DatabaseTableDeleter tableDeleter = new DatabaseTableDeleter();
        tableDeleter.dropDatabaseTables(connection);
        tableCreator.createDatabaseTables(connection);
        DataGeneratorUtil dataGenerator = new DataGeneratorUtil();
        Set<Group> generatedGroups = dataGenerator.generateGroups();
        List<Course> generatedCourses = dataGenerator.generateCourses();
        Set<Student> generatedStudents = dataGenerator.generateStudents();
        dataGenerator.addGeneratedGroupsToDatabase(generatedGroups);
        dataGenerator.addGeneratedCoursesToDatabase(generatedCourses);
        dataGenerator.addGeneratedStudentsToDatabase(generatedStudents);
        dataGenerator.setRandomCoursesForStudents();
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
        queryExecutor.executeQuery();
    }
}
