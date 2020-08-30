package com.example.demo.service.impl;

import com.example.demo.pojo.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService
{

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        List<Course> result=new ArrayList<Course>();
        List<Object[]> courseByUser = courseRepository.findAllCourse();
        for (Object[] co:courseByUser) {
            Course course = new Course();
            course.setId(Long.valueOf((Integer)co[0]));
            course.setName((String) co[1]);
            if (co[2]!=null){
                course.setSubscription(((BigInteger) co[2]).intValue());
            }
            result.add(course);
        }
        return result;
    }

    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findCourseById(int id) {
        return courseRepository.findById((long) id).get();
    }

    @Override
    public void update(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void removeCourseById(int id) {
        courseRepository.deleteById((long) id);
    }

    @Override
    public List<Course> findCourseByUser(Long userid) {
        List<Course> result=new ArrayList<Course>();
        List<Object[]> courseByUser = courseRepository.findCourseByUser(userid);
        for (Object[] co:courseByUser) {
            Course course = new Course();
            course.setId(Long.valueOf((Integer)co[0]));
            course.setName((String) co[1]);
            if (co[2]!=null){
                course.setSubscription(((BigInteger) co[2]).intValue());
            }
            result.add(course);
        }
        return result;
    }
}
