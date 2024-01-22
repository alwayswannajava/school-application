package mainClasses;

import db.DatabaseConnector;
import entity.Course;
import entity.Group;
import entity.Student;
import net.datafaker.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class DataGeneratorUtil {
    private static final DatabaseConnector connector = new DatabaseConnector();
    private static final String GENERATE_GROUP_NAME_REGEX_PATTERN = "([A-Z]{2})-([0-9]{2})";
    private Set<Group> generatedGroups = new HashSet<>();
    private List<Course> generatedCourses = new ArrayList<>();
    private Set<Student> generatedStudents = new HashSet<>();
    private static final String INSERT_TO_GROUP_TABLE_QUERY = "INSERT INTO groups (group_id, group_name) values (?, ?);";
    private static final String INSERT_TO_COURSES_TABLE_QUERY = "INSERT INTO courses (course_id, course_name, course_description) values (?, ?, ?);";
    public static final String INSERT_TO_STUDENTS_TABLE_QUERY = "INSERT INTO students (group_id, first_name, last_name) values (?, ?, ?)";
    public static final String INSERT_TO_STUDENTS_COURSES_TABLE_QUERY = "INSERT INTO students_courses (student_id, course_id) values (?, ?)";
    private static final int RANDOM_GENERATED_STUDENTS_COUNT_RANGE = 20;
    private static final int COUNT_RANDOM_GENERATED_COURSES_REGEX = 3;
    private static final int ID_RANDOM_COURSE_RANGE = 9;
    private static final int ID_RANDOM_GROUP_RANGE = 10;
    private static final int MAX_COUNT_STUDENTS = 200;

    public Set<Group> generateGroups() {
        Faker faker = new Faker();
        generatedGroups.add(new Group(0, "Empty_group"));
        generatedGroups.add(new Group(1, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(2, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(3, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(4, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(5, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(6, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(7, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(8, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(9, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        generatedGroups.add(new Group(10, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN)));
        return generatedGroups;
    }

    public List<Course> generateCourses() {
        generatedCourses.add(new Course(1, "Math", "Math course"));
        generatedCourses.add(new Course(2, "Physical Education", "Physical education course"));
        generatedCourses.add(new Course(3, "Physics", "Physics course"));
        generatedCourses.add(new Course(4, "English", "English course"));
        generatedCourses.add(new Course(5, "History", "History course"));
        generatedCourses.add(new Course(6, "Information technology", "Information technology course"));
        generatedCourses.add(new Course(7, "Art", "Art course"));
        generatedCourses.add(new Course(8, "Geography", "Geography course"));
        generatedCourses.add(new Course(9, "Chemistry", "Chemistry course"));
        generatedCourses.add(new Course(10, "Literature", "Literature course"));
        return generatedCourses;
    }

    public Set<Student> generateStudents() {
        Faker faker = new Faker();
        Random random = new Random();
        int countStudents;
        int idRandomGroup;
        while (generatedStudents.size() != MAX_COUNT_STUDENTS) {
            int countStudentsLeft = MAX_COUNT_STUDENTS - generatedStudents.size();
            countStudents = random.nextInt(RANDOM_GENERATED_STUDENTS_COUNT_RANGE) + 10;
            idRandomGroup = random.nextInt(ID_RANDOM_GROUP_RANGE);
            if (countStudentsLeft < countStudents) {
                countStudents = countStudentsLeft;
            }
            for (int i = 0; i < countStudents; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                generatedStudents.add(new Student(idRandomGroup, firstName, lastName));
            }
        }
        return generatedStudents;
    }

    public void addGeneratedGroupsToDatabase() {
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TO_GROUP_TABLE_QUERY)) {
            for (Group currentInfoGroup : generatedGroups) {
                int groupId = currentInfoGroup.getGroupId();
                String groupName = currentInfoGroup.getGroupName();
                preparedStatement.setInt(1, groupId);
                preparedStatement.setString(2, groupName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addGeneratedCoursesToDatabase() {
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TO_COURSES_TABLE_QUERY)) {
            for (Course currentCourse : generatedCourses) {
                int courseId = currentCourse.getCourseId();
                String courseName = currentCourse.getCourseName();
                String courseDesciption = currentCourse.getCourseDescription();
                preparedStatement.setInt(1, courseId);
                preparedStatement.setString(2, courseName);
                preparedStatement.setString(3, courseDesciption);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addGeneratedStudentsToDatabase() {
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TO_STUDENTS_TABLE_QUERY)) {
            for (Student currentStudent : generatedStudents) {
                int studentGroupId = currentStudent.getGroupId();
                String studentFirstName = currentStudent.getFirstName();
                String studentLastName = currentStudent.getLastName();
                preparedStatement.setInt(1, studentGroupId);
                preparedStatement.setString(2, studentFirstName);
                preparedStatement.setString(3, studentLastName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setRandomCoursesForStudents() {
        int idRandomCourse;
        Random random = new Random();
        Set<Integer> lastIdRandomCourseSet = new HashSet<>();
        try (Connection connection = connector.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TO_STUDENTS_COURSES_TABLE_QUERY)) {
            for (int currentStudentId = 0; currentStudentId < 200; currentStudentId++) {
                lastIdRandomCourseSet.clear();
                int countRandomCourses = random.nextInt(COUNT_RANDOM_GENERATED_COURSES_REGEX) + 1;
                for (int j = 0; j < countRandomCourses; j++) {
                    idRandomCourse = random.nextInt(ID_RANDOM_COURSE_RANGE) + 1;
                    while (lastIdRandomCourseSet.contains(idRandomCourse)) {
                        idRandomCourse = random.nextInt(ID_RANDOM_COURSE_RANGE) + 1;
                    }
                    preparedStatement.setInt(1, currentStudentId + 1);
                    preparedStatement.setInt(2, idRandomCourse);
                    preparedStatement.executeUpdate();
                    lastIdRandomCourseSet.add(idRandomCourse);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
