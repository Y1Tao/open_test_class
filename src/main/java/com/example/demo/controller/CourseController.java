package com.example.demo.controller;

import com.example.demo.pojo.Course;
import com.example.demo.pojo.CustomUser;
import com.example.demo.security.IsTeacher;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@IsTeacher // 表明该控制器下所有请求都需要老师角色才能访问
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 课程管理首页
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String admin(Model model) {
        //获取用户信息
        List<Course> courseList = courseService.findAll();

        CustomUser user = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("courseList", courseList);

        return "course/index";
    }

    @GetMapping("/createPage")
    public String createPage() {
        return "course/create";
    }

    /**
     * 新增
     * @param course
     * @return
     */
    @PostMapping("/create")
    public String create(Course course){
        Course save = courseService.create(course);
        return "redirect:/course/index";
    }

    /**
     * 根据id查询
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/findCourseById/{id}")
    public String findCourseById(@PathVariable("id") int id, Model model){
        Course course = courseService.findCourseById(id);
        model.addAttribute("course",course);
        return "course/edit";
    }

    /**
     * 更新
     * @param course
     * @return
     */
    @PutMapping("/update")
    public String update(Course course) {
        courseService.update(course);
        return "redirect:/course/index";
    }

    @GetMapping("/removeCourseById")
    public String removeCourseById(int id){
        courseService.removeCourseById(id);
        return "redirect:/course/index";
    }

}
