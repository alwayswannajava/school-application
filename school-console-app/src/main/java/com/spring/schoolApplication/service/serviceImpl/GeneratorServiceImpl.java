package com.spring.schoolApplication.service.serviceImpl;

import com.spring.schoolApplication.DataGeneratorUtil;
import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.entity.Student;
import com.spring.schoolApplication.entity.StudentCourse;
import com.spring.schoolApplication.service.GeneratorService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class GeneratorServiceImpl implements GeneratorService {
    private static final String GENERATE_GROUP_NAME_REGEX_PATTERN = "([A-Z]{2})-([0-9]{2})";
    private static final int MAX_COUNT_GROUPS = 11;
    private static final int RANDOM_GENERATED_STUDENTS_COUNT_RANGE = 20;
    private static final int COUNT_RANDOM_GENERATED_COURSES_RANGE = 3;
    private static final int ID_RANDOM_COURSE_RANGE = 9;
    private static final int ID_RANDOM_GROUP_RANGE = 10;
    private static final int MAX_COUNT_STUDENTS = 200;

    @Autowired
    private DataGeneratorUtil generatorRepository;


    @Override
    public void generateGroups() {
        Faker faker = new Faker();
        for (int groupId = 1; groupId < MAX_COUNT_GROUPS; groupId++) {
            Group currentRandomGroup = new Group(groupId, faker.regexify(GENERATE_GROUP_NAME_REGEX_PATTERN));
            generatorRepository.addGeneratedGroupToDatabase(currentRandomGroup);
        }
    }

    @Override
    public void generateCourses() {
        generatorRepository.addGeneratedCoursesToDatabase();
    }

    @Override
    public void generateStudents() {
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
                Student currentStudent = new Student(idRandomGroup, firstName, lastName);
                generatorRepository.addGeneratedStudentToDatabase(currentStudent);
                index++;
            }
        }
    }

    @Override
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
                StudentCourse studentCourse = new StudentCourse(currentStudentId + 1, idRandomCourse);
                generatorRepository.setRandomCoursesForStudents(studentCourse);
                lastIdRandomCourseSet.add(idRandomCourse);
            }
        }
    }
}
