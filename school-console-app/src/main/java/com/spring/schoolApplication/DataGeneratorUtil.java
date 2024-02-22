package com.spring.schoolApplication;
import com.spring.schoolApplication.entity.Group;
import net.datafaker.Faker;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DataGeneratorUtil {
    private static final String GENERATE_GROUP_NAME_REGEX_PATTERN = "([A-Z]{2})-([0-9]{2})";
    public static final String INSERT_TO_GROUP_TABLE_QUERY = "INSERT INTO groups (group_id, group_name) values (?, ?);";
    public static final String INSERT_TO_COURSES_TABLE_QUERY = "INSERT INTO courses (course_id, course_name, course_description) values (?, ?, ?);";
    public static final String INSERT_TO_STUDENTS_TABLE_QUERY = "INSERT INTO students (group_id, first_name, last_name) values (?, ?, ?)";
    public static final String INSERT_TO_STUDENTS_COURSES_TABLE_QUERY = "INSERT INTO students_courses (student_id, course_id) values (?, ?)";
    private static final int RANDOM_GENERATED_STUDENTS_COUNT_RANGE = 20;
    private static final int COUNT_RANDOM_GENERATED_COURSES_RANGE = 3;
    private static final int ID_RANDOM_COURSE_RANGE = 9;
    private static final int ID_RANDOM_GROUP_RANGE = 10;
    private static final int MAX_COUNT_STUDENTS = 200;
    private static final int MAX_COUNT_GROUPS = 11;

    private JdbcTemplate jdbcTemplate;


    public DataGeneratorUtil(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addGeneratedGroupsToDatabase() {
        Faker faker = new Faker();
        jdbcTemplate.update(INSERT_TO_GROUP_TABLE_QUERY, 0, "Empty group");
        for (int groupId = 1; groupId < MAX_COUNT_GROUPS; groupId++) {
            Group currentRandomGroup = new Group(groupId, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN));
            jdbcTemplate.update(INSERT_TO_GROUP_TABLE_QUERY, currentRandomGroup.getGroupId(), currentRandomGroup.getGroupName());
        }
    }

    public void addGeneratedCoursesToDatabase() {
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 1, "Math", "Math course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 2, "Physical Education", "Physical education course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 3, "Physics", "Physics course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 4, "English", "English course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 5, "History", "History course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 6, "Information technology", "Information technology course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 7, "Art", "Art course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 8, "Geography", "Geography course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 9, "Chemistry", "Chemistry course");
        jdbcTemplate.update(INSERT_TO_COURSES_TABLE_QUERY, 10, "Literature", "Literature course");
    }

    public void addGeneratedStudentsToDatabase() {
        Faker faker = new Faker();
        Random random = new Random();
        int countStudents;
        int idRandomGroup;
        int index = 0;
        while (index != MAX_COUNT_STUDENTS) {
            int countStudentsLeft = MAX_COUNT_STUDENTS - index;
            countStudents = random.nextInt(RANDOM_GENERATED_STUDENTS_COUNT_RANGE) + 10;
            idRandomGroup = random.nextInt(ID_RANDOM_GROUP_RANGE);
            if (countStudentsLeft < countStudents) {
                countStudents = countStudentsLeft;
            }
            for (int i = 0; i < countStudents; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                jdbcTemplate.update(INSERT_TO_STUDENTS_TABLE_QUERY, idRandomGroup, firstName, lastName);
                index++;
            }
        }
    }

    public void setRandomCoursesForStudents() {
        int idRandomCourse;
        Random random = new Random();
        Set<Integer> lastIdRandomCourseSet = new HashSet<>();
        for (int currentStudentId = 0; currentStudentId < MAX_COUNT_STUDENTS; currentStudentId++) {
            lastIdRandomCourseSet.clear();
            int countRandomCourses = random.nextInt(COUNT_RANDOM_GENERATED_COURSES_RANGE) + 1;
            for (int j = 0; j < countRandomCourses; j++) {
                idRandomCourse = random.nextInt(ID_RANDOM_COURSE_RANGE) + 1;
                while (lastIdRandomCourseSet.contains(idRandomCourse)) {
                    idRandomCourse = random.nextInt(ID_RANDOM_COURSE_RANGE) + 1;
                }
                jdbcTemplate.update(INSERT_TO_STUDENTS_COURSES_TABLE_QUERY, currentStudentId + 1, idRandomCourse);
                lastIdRandomCourseSet.add(idRandomCourse);
            }
        }
    }

}
