package com.nayak.samplespringbootapp.mapper;

import com.nayak.samplespringbootapp.model.Student;
import com.nayak.samplespringbootapp.model.StudentDto;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    StudentDto studentToStudentDto(Student student);

    Student studentDtoToStudent(StudentDto studentDto);
}
