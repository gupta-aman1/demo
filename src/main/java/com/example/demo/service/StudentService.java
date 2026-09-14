package com.example.demo.service;

import com.example.demo.model.StudentRequest;
import com.example.demo.model.StudentResponse;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private boolean isAdult;
    public StudentResponse createStudent(StudentRequest request) {

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
