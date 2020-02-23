package com.nayak.samplespringbootapp.controller;

import com.nayak.samplespringbootapp.model.Student;
import com.nayak.samplespringbootapp.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerWebMvcTest {
    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    private Student student1;
    private Student student2;

    @AfterEach
    void tearDown() {
        reset(studentService);
    }

    @BeforeEach
    void setUp() {
        student1 = Student.builder().id(1).name("a").age(1).weight(10).height(29).build();
        student2 = Student.builder().id(2).name("b").age(2).weight(15).height(30).build();

    }

    @Test
    @DisplayName("List of Students")
    void getAllStudentTest() throws Exception {
        given(studentService.getAllStudents()).willReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/api/v1/students")).andExpect(status().isOk()).andDo(print());
    }
}
