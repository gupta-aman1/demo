package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository
        extends JpaRepository<StudentEntity, Long> {


    List<StudentEntity> findByName(String name);

    List<StudentEntity> findByAgeGreaterThan(int age);


}