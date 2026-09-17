package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
import com.example.demo.model.Student;
import com.example.demo.model.StudentRequest;
import com.example.demo.model.StudentResponse;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request){
     StudentResponse student =  studentService.createStudent(request);

     return ResponseEntity.status(HttpStatus.CREATED)
             .body(student);


    }


    @GetMapping
    public List<StudentResponse> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        StudentResponse student = studentService.getStudentById(id);

        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        StudentResponse student = studentService.updateStudent(id, request);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
