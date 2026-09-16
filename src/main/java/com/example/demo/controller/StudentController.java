package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
import com.example.demo.model.Student;
import com.example.demo.model.StudentRequest;
import com.example.demo.model.StudentResponse;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {


    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;

        }


    @PostMapping
    public StudentResponse createStudent( @Valid @RequestBody StudentRequest request){

        return studentService.createStudent(request);
    }

    @GetMapping
    public List<StudentEntity> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public StudentEntity getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
