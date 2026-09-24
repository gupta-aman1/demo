package com.example.demo.service;

import com.example.demo.entity.CourseEntity;
import com.example.demo.model.CourseResponse;
import com.example.demo.model.StudentResponse;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {

        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Course not found with id: " + id
                ));

        List<StudentResponse> students =
                course.getStudents()
                        .stream()
                        .map(student -> new StudentResponse(
                                student.getName(),
                                student.getAge(),
                                student.getAge() >= 18
                        ))
                        .toList();

        return new CourseResponse(
                course.getId(),
                course.getName(),
                students
        );
    }


    @Transactional(readOnly = true)
    public List<CourseResponse> getCourses() {

        List<CourseEntity> courses =
                courseRepository.findAll();

        return courses.stream()
                .map(course -> {

                    List<StudentResponse> students =
                            course.getStudents()
                                    .stream()
                                    .map(student -> new StudentResponse(
                                            student.getName(),
                                            student.getAge(),
                                            student.getAge() >= 18
                                    ))
                                    .toList();

                    return new CourseResponse(
                            course.getId(),
                            course.getName(),
                            students
                    );
                })
                .toList();
    }
}
