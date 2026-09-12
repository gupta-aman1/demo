package com.example.demo.controller;

import com.example.demo.model.Student;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @PostMapping
    public Student createStudent(@RequestBody Student student){


        System.out.println("Student name: " + student.getName());
        System.out.println("Student age: " + student.getAge());

        return student;
    }
}
