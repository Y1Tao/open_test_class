package com.example.demo.service;

import com.example.demo.pojo.Subscribe;

public interface SubscribeService {

    void insert(Subscribe subscribe);

    void remove(Long courseid);
}
