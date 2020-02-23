package com.nayak.samplespringbootapp.controller;

import com.nayak.samplespringbootapp.model.Student;
import com.nayak.samplespringbootapp.model.StudentDto;
import com.nayak.samplespringbootapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity students() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity studentById(@PathVariable("id") long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/student/{id}")
    public ResponseEntity updateStudent(@PathVariable("id") long id, @RequestBody @Validated StudentDto studentDto) {
        studentService.updateStudent(id, studentDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/student")
    public ResponseEntity createStudent(@RequestBody @Validated StudentDto studentDto) {

        Student student = studentService.saveStudent(studentDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
