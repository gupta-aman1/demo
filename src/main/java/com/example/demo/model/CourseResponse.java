package com.example.demo.model;

import java.util.List;

public class CourseResponse {

    private Long id;
    private String name;
    private List<StudentResponse> students;

    public CourseResponse(
            Long id,
            String name,
            List<StudentResponse> students) {

        this.id = id;
        this.name = name;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StudentResponse> getStudents() {
        return students;
    }
}