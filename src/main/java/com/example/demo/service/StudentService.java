package com.example.demo.service;

import com.example.demo.model.StudentRequest;
import com.example.demo.model.StudentResponse;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    public StudentService() {
       // System.out.println("StudentService object created");
        System.out.println("StudentService object created: " + this);
    }

    public StudentResponse createStudent(StudentRequest request) {
        boolean isAdult;
        if(request.getAge() > 28){
                isAdult = true;
        }
        else
        {
            isAdult = false;
        }
        return new StudentResponse(
                request.getName(),
                request.getAge(),
                isAdult
        );
    }
}
