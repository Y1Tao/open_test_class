package com.example.demo.controller;

import com.example.demo.pojo.CustomUser;
import com.example.demo.pojo.User;
import com.example.demo.security.IsAdmin;
import com.example.demo.security.IsEditor;
import com.example.demo.security.IsReviewer;
import com.example.demo.security.IsUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@IsUser // 表明该控制器下所有请求都需要登入后才能访问
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registPage")
    @IsAdmin
    public String registPage() {
        return "user/register";
    }

    @PostMapping("/register")
    @IsAdmin
    public String registerUser(User user){
        // 记得注册的时候把密码加密一下
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userService.create(user);
        return "redirect:/user/admin";
    }

    @GetMapping("/findUserById/{id}")
    @IsAdmin
    public String findUserById(@PathVariable("id") int id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user",user);
        return "user/edit";
    }

    /**
     * 更新账户
     * @param user
     * @return
     */
    @PutMapping("/updateUser")
    @IsAdmin
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/user/admin";
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
    public String admin(Model model) {
        //获取用户信息
        List<User> userList = userService.findAll();

        CustomUser user = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("userList", userList);

        return "user/admin";
    }
}
