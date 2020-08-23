package com.example.demo.controller;

import com.example.demo.pojo.CustomUser;
import com.example.demo.pojo.User;
import com.example.demo.security.IsAdmin;
import com.example.demo.security.IsEditor;
import com.example.demo.security.IsReviewer;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

//    @RequestMapping()
//    public String login(){
//String username="";
//        userService.findByUsername(username);
//        return null;
//    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));
        // 记得注册的时候把密码加密一下
        user.setPassword(passwordEncoder.encode(registerUser.get("password")));
        user.setRole(registerUser.get("role"));
        User save = userService.create(user);
        return save.toString();
    }

    @GetMapping("/home")
    public String home(Model model) {
        // 方法一：通过SecurityContextHolder获取
        CustomUser user = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user/home";
    }

    @GetMapping("/editor")
    @IsEditor
    public String editor(Authentication authentication, Model model) {
        // 方法二：通过方法注入的形式获取Authentication
        CustomUser user = (CustomUser)authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user/editor";
    }

    @GetMapping("/reviewer")
    @IsReviewer
    public String reviewer(Principal principal, Model model) {
        // 方法三：同样通过方法注入的方法，注意要转型，此方法很二，不推荐
        CustomUser user = (CustomUser) ((Authentication)principal).getPrincipal();
        model.addAttribute("user", user);
        return "user/reviewer";
    }

    @GetMapping("/admin")
    @IsAdmin
    public String admin() {
        // 方法四：通过Thymeleaf的Security标签进行，详情见admin.html
        return "user/admin";
    }
}
