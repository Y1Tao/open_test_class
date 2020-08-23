package com.example.demo.service;

import com.example.demo.pojo.User;

public interface UserService {
    User findByUsername(String username);

    User create(User user);
}
