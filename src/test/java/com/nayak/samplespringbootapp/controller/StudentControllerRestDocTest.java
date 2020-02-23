package com.nayak.samplespringbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nayak.samplespringbootapp.model.Student;
import com.nayak.samplespringbootapp.model.StudentDto;
import com.nayak.samplespringbootapp.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(controllers = StudentController.class)
@ComponentScan(basePackages = "com.nayak.samplespringbootapp.mapper")
public class StudentControllerRestDocTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentService studentService;

    @Test
    public void basicTest() throws Exception {

        when(studentService.getStudentById(anyLong())).thenReturn(Student.builder().id(1).name("a").age(11).height(123).weight(70).build());

        mockMvc.perform(get("/api/v1/student/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("v1/student-get",
                        pathParameters(
                                parameterWithName("id").description("Student ID of Student to be fetched")
                        ),
                        requestParameters(),
                        responseFields(
                                fieldWithPath("id").description("Student ID"),
                                fieldWithPath("name").description("Student Name"),
                                fieldWithPath("age").description("Student Age"),
                                fieldWithPath("height").description("Student Height"),
                                fieldWithPath("weight").description("Student Weight")
                        )
                ));
    }

    @Test
    public void updateStudentTest() throws Exception {
        when(studentService.saveStudent(any(StudentDto.class)) ).thenReturn(Student.builder().build());
        ConstrainedFields fields = new ConstrainedFields(StudentDto.class);
        String studentDtoJson = objectMapper.writeValueAsString( StudentDto.builder().name("abcd").age(28).height(123).weight(70).build());
        mockMvc.perform(post("/api/v1/student").contentType(MediaType.APPLICATION_JSON_VALUE).content(studentDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/student-create",
                        requestFields(
                                fields.withPath("name").description("Student Name"),
                                fields.withPath("age").description("Student Age"),
                                fields.withPath("height").description("Student Height"),
                                fields.withPath("weight").description("Student Weight")
                        )
                ));

    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}
