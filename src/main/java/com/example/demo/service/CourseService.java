package com.example.demo.service;

import com.example.demo.pojo.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course create(Course course);

    Course findCourseById(int id);

    void update(Course course);

    void removeCourseById(int id);

    List<Course> findCourseByUser(Long userid);
}
