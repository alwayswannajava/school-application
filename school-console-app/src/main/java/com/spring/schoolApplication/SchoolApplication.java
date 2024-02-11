package com.spring.schoolApplication;

import com.spring.schoolApplication.dao.CourseDao;
import com.spring.schoolApplication.dao.GroupDao;
import com.spring.schoolApplication.dao.StudentDao;
import com.spring.schoolApplication.entity.Course;
import com.spring.schoolApplication.entity.Group;
import com.spring.schoolApplication.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolApplication implements CommandLineRunner {

	@Autowired
	StudentDao studentDao;
	@Autowired
	GroupDao groupDao;
	@Autowired
	CourseDao courseDao;
	@Autowired
	QueryExecutor queryExecutor;


	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		studentDao.create(new Student(30, 2, "Ivan", "Cirko"));
		studentDao.addStudentToCourse(30, 2);
		studentDao.addStudentToCourse(30, 3);
		studentDao.removeStudentFromCourse(30, 2);
		groupDao.create(new Group(6, "WT-26"));
		courseDao.create(new Course(14, "Swimming", "Swimming course"));
		queryExecutor.executor();
	}
}
