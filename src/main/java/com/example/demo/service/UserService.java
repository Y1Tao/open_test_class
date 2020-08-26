package com.example.demo.service;

import com.example.demo.pojo.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User create(User user);

    List<User> findAll();

    User findUserById(int id);

    void updateUser(User user);
}
