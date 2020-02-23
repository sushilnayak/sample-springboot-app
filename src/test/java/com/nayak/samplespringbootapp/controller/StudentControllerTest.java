package com.nayak.samplespringbootapp.controller;

import com.nayak.samplespringbootapp.model.Student;
import com.nayak.samplespringbootapp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = Student.builder().id(1).name("a").age(1).weight(10).height(29).build();
        student2 = Student.builder().id(2).name("b").age(2).weight(15).height(30).build();

        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    @DisplayName("Testing All Student Information Fetching Successfully")
    void allStudentTest() throws Exception {
        given(studentService.getAllStudents()).willReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("a")))
                .andExpect(jsonPath("$.[0].age", equalTo(1)))
                .andExpect(jsonPath("$.[0].weight", equalTo(10.0)))
                .andExpect(jsonPath("$.[0].height", equalTo(29.0)))

                .andExpect(jsonPath("$.[1].id", equalTo(2)))
                .andExpect(jsonPath("$.[1].name", equalTo("b")))
                .andExpect(jsonPath("$.[1].age", equalTo(2)))
                .andExpect(jsonPath("$.[1].weight", equalTo(15.0)))
                .andExpect(jsonPath("$.[1].height", equalTo(30.0)))
//                ;
                .andDo(print());
    }
}