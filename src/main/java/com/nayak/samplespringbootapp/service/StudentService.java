package com.nayak.samplespringbootapp.service;

import com.nayak.samplespringbootapp.model.Student;
import com.nayak.samplespringbootapp.model.StudentDto;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(long id);

    void updateStudent(long id, StudentDto studentDto);

    Student saveStudent(StudentDto studentDto);

    void deleteStudent(long id);
}
