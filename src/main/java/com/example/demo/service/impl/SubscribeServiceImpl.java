package com.example.demo.service.impl;

import com.example.demo.pojo.Subscribe;
import com.example.demo.repository.SubscribeRepository;
import com.example.demo.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    SubscribeRepository subscribeRepository;

    @Override
    public void insert(Subscribe subscribe) {
        subscribeRepository.save(subscribe);
    }

    @Transactional
    @Override
    public void remove(Long courseid) {
        subscribeRepository.deleteByCourseId(courseid);
    }
}
