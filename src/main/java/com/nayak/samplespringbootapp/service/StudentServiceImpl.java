package com.nayak.samplespringbootapp.service;

import com.nayak.samplespringbootapp.mapper.StudentMapper;
import com.nayak.samplespringbootapp.model.Student;
import com.nayak.samplespringbootapp.model.StudentDto;
import com.nayak.samplespringbootapp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(long id) {
        return studentRepository.findStudentById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateStudent(long id, StudentDto studentDto) {
        Student student = studentMapper.studentDtoToStudent(studentDto);
        student.setId(id);
        studentRepository.update(student);
    }

    @Transactional(readOnly = false)
    @Override
    public Student saveStudent(StudentDto studentDto) {
        long studentId = studentRepository.save(studentMapper.studentDtoToStudent(studentDto));
        return studentRepository.findStudentById(studentId);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteStudent(long id) {
        studentRepository.delete(id);
    }
}
