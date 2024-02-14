package com.spring.schoolApplication.entity;

import java.util.Objects;

public class StudentCourse {

    private int student_id;
    private int course_id;

    public StudentCourse() {

    }

    public StudentCourse(int student_id, int course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourse)) return false;
        StudentCourse that = (StudentCourse) o;
        return student_id == that.student_id && course_id == that.course_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student_id, course_id);
    }
}
