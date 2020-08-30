package com.example.demo.repository;


import com.example.demo.pojo.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long>, JpaSpecificationExecutor<Subscribe> {

    void deleteByCourseId(Long courseid);
}
