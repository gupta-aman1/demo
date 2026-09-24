package com.example.demo.service;

import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.exception.StudentNotFoundExceptioN;
import com.example.demo.model.StudentRequest;
import com.example.demo.model.StudentResponse;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class StudentService {

  /*  public StudentService() {
       // System.out.println("StudentService object created");
        System.out.println("StudentService object created: " + this);
    }*/

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository,CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        System.out.println("Student Service object created");

    }

    public StudentResponse createStudent(StudentRequest request) {


        StudentEntity student = new StudentEntity(
                request.getName(),
                request.getAge()
        );

        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException(
                        "Course not found with id: " + request.getCourseId()
                ));

        student.setCourse(course);

        StudentEntity savedStudent = studentRepository.save(student);

  //      boolean adult = savedStudent.getAge() >= 18;

//        return new StudentResponse(
//                savedStudent.getName(),
//                savedStudent.getAge(),
//                adult
//        );
        return toResponse(savedStudent);
    }

    public List<StudentResponse> getStudents() {
        List<StudentEntity> students = studentRepository.findAll();
       /* return students.stream()
                .map(student -> new StudentResponse(
                        student.getName(),
                        student.getAge(),
                        student.getAge() >= 25
                ))
                .toList();*/

        return students.stream()
                .map(this::toResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long id) {

        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundExceptioN(
                                "Student not found with id: " + id
                        )
                );

       /*return new StudentResponse(
                student.getName(),
                student.getAge(),
                student.getAge() >= 18
        );*/

        return toResponse(student);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {

        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundExceptioN(
                                "Student not found with id: " + id
                        )
                );

        student.setName(request.getName());
        student.setAge(request.getAge());

        StudentEntity updatedStudent = studentRepository.save(student);

//        boolean adult = updatedStudent.getAge() >= 18;

//        return new StudentResponse(
//                updatedStudent.getName(),
//                updatedStudent.getAge(),
//                adult
//        );

        return toResponse(updatedStudent);
    }

    public void deleteStudent(Long id) {

        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundExceptioN(
                                "Student not found with id: " + id
                        )
                );

        studentRepository.delete(student);
    }


    private StudentResponse toResponse(StudentEntity student) {

        return new StudentResponse(
                student.getName(),
                student.getAge(),
                student.getAge() >= 18
        );
    }
}
