package com.nayak.samplespringbootapp.repository;

import com.nayak.samplespringbootapp.model.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> findAll();

    Student findStudentById(long id);

    long update(Student student);

    long save(Student student);

    void delete(long id);
}
