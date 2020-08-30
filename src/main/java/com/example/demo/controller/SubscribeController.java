package com.example.demo.controller;

import com.example.demo.pojo.Course;
import com.example.demo.pojo.CustomUser;
import com.example.demo.pojo.Subscribe;
import com.example.demo.security.IsStudent;
import com.example.demo.service.CourseService;
import com.example.demo.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@IsStudent
@Controller
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    SubscribeService subscribeService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/index")
    public String admin(Model model) {
        //获取用户信息
        CustomUser user = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Course> courseList = courseService.findCourseByUser(user.getId());


        model.addAttribute("user", user);
        model.addAttribute("courseList", courseList);
        return "subscribe/index";
    }

    @GetMapping("/insert")
    public String insert(Long courseid){
        //获取用户信息
        CustomUser user = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Subscribe subscribe=new Subscribe();
        subscribe.setCourseId(courseid);
        subscribe.setUserId(user.getId());
        subscribeService.insert(subscribe);
        return "redirect:/subscribe/index";
    }

    @GetMapping("/remove")
    public String remove(Long courseid){
        subscribeService.remove(courseid);
        return "redirect:/subscribe/index";
    }
}
