package com.example.demo.repository;

import com.example.demo.pojo.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    @Query(value = "select course.*,sub.userid from course left join (select * from subscribe where userid=?1) sub on course.id=sub.courseid", nativeQuery = true)
    List<Object[]> findCourseByUser(Long userid);
    @Query(value = "select cou .id,cou .name,count(cou .userid) from (select course.*,sub.userid from course left join subscribe sub on course.id=sub.courseid) as cou group by cou .id,cou .name", nativeQuery = true)
    List<Object[]> findAllCourse();
}
