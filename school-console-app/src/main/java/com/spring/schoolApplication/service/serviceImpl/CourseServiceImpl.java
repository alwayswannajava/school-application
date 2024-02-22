package com.spring.schoolApplication.service.serviceImpl;
import com.spring.schoolApplication.dao.jdbcDao.JdbcCourseDao;
import com.spring.schoolApplication.service.CourseService;
import com.spring.schoolApplication.entity.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private JdbcCourseDao courseRepository;

    public CourseServiceImpl(JdbcCourseDao courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public int create(Course course) {
        return courseRepository.create(course);
    }

    @Override
    public int countAllCourses() {
        return courseRepository.countAllCourses();
    }
}
