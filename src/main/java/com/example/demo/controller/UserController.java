package com.example.demo.controller;

import com.example.demo.pojo.CustomUser;
import com.example.demo.pojo.User;
import com.example.demo.security.IsAdmin;
import com.example.demo.security.IsUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @IsAdmin
    public String registerUser(User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userService.create(user);
        return "redirect:/user/admin";
    }

    /**
     * 根据id查询用户
     * @param id
     * @param model
     * @return
     */
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

    /**
     * 删除用户
     * @param id
     * @return
     */
    @GetMapping("/removeUserById")
    @IsAdmin
    public String removeUserById(int id){
        userService.removeUserById(id);
        return "redirect:/user/admin";
    }

    @GetMapping("/home")
    public String home(Model model) {
        CustomUser user = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        model.addAttribute("user", user);
        return "user/home";
    }

    /**
     * 用户管理界面
     * @param model
     * @return
     */
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
