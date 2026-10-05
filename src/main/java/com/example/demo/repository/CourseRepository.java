package com.example.demo.repository;

import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository  extends JpaRepository<CourseEntity, Long> {

}
