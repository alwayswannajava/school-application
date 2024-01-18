package mainClasses;

import entity.Course;
import entity.Group;
import entity.Student;
import net.datafaker.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataGeneratorUtil {
    private static final String GENERATE_GROUP_NAME_REGEX_PATTERN = "([A-Z]{2})-([0-9]{2})";
    private Set<Group> generatedGroups = new LinkedHashSet<>();
    private List<Course> generatedCourses = new LinkedList<>();
    private Set<Student> generatedStudents = new LinkedHashSet<>();
    private static final String INSERT_TO_GROUP_TABLE_QUERY = "INSERT INTO groups (group_id, group_name) values (?, ?);";
    private static final String INSERT_TO_COURSES_TABLE_QUERY = "INSERT INTO courses (course_id, course_name, course_description) values (?, ?, ?);";
    private static final String INSERT_TO_STUDENTS_TABLE_QUERY = "INSERT INTO students (student_id, group_id, first_name, last_name) values (?, ?, ?, ?)";
    private static final String INSERT_TO_STUDENTS_COURSES_TABLE_QUERY = "INSERT INTO students_courses (student_id, course_id) values (?, ?)";
    private static final int RANDOM_GENERATED_STUDENTS_COUNT_RANGE = 30 - 10;
    private static final int COUNT_RANDOM_GENERATED_COURSES_REGEX = 3 - 1;
    private static final int RANDOM_ID_COURSE_REGEX = 10 - 1;

    public void generateGroups() {
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
    }

    public void generateCourses() {
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
    }

    public void generateStudents() {
        Faker faker = new Faker();
        Random random = new Random();
        int groupId = 0;
        int studentIndex = 1;
        int randomGeneratedStudents = random.nextInt(RANDOM_GENERATED_STUDENTS_COUNT_RANGE) + 10;
        while (generatedStudents.size() != 200) {
            groupId++;
            for (int i = 0; i < randomGeneratedStudents; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                if (groupId < 11) {
                    if (generatedStudents.size() == 200) {
                        break;
                    }
                    generatedStudents.add(new Student(studentIndex, groupId, firstName, lastName));
                    studentIndex++;
                } else {
                    if (generatedStudents.size() == 200) {
                        break;
                    }
                    firstName = faker.name().firstName();
                    lastName = faker.name().lastName();
                    generatedStudents.add(new Student(studentIndex, 0, firstName, lastName));
                    studentIndex++;
                }
            }
        }

    }

    public void addGeneratedGroupsToDatabase(Connection connection) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(INSERT_TO_GROUP_TABLE_QUERY);
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

    public void addGeneratedCoursesToDatabase(Connection connection) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(INSERT_TO_COURSES_TABLE_QUERY);
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

    public void addGeneratedStudentsToDatabase(Connection connection) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(INSERT_TO_STUDENTS_TABLE_QUERY);
            for (Student currentStudent : generatedStudents) {
                int studentId = currentStudent.getStudentId();
                int studentGroupId = currentStudent.getGroupId();
                String studentFirstName = currentStudent.getFirstName();
                String studentLastName = currentStudent.getLastName();
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, studentGroupId);
                preparedStatement.setString(3, studentFirstName);
                preparedStatement.setString(4, studentLastName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setRandomCoursesForStudents(Connection connection) {
        PreparedStatement preparedStatement;
        int idRandomCourse = 0;
        int lastRandomIdCourse = 0;
        Random random = new Random();
        try {
            preparedStatement = connection.prepareStatement(INSERT_TO_STUDENTS_COURSES_TABLE_QUERY);
            for (Student currentStudent : generatedStudents) {
                int countRandomCourses = random.nextInt(COUNT_RANDOM_GENERATED_COURSES_REGEX) + 1;
                int studentId = currentStudent.getStudentId();
                preparedStatement.setInt(1, studentId);
                for (int i = 0; i < countRandomCourses; i++) {
                    idRandomCourse = random.nextInt(RANDOM_ID_COURSE_REGEX) + 1;
                    if (lastRandomIdCourse == idRandomCourse) {
                        idRandomCourse = random.nextInt(RANDOM_ID_COURSE_REGEX) + 1;
                    }
                    preparedStatement.setInt(2, idRandomCourse);
                    preparedStatement.executeUpdate();
                    lastRandomIdCourse = idRandomCourse;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
