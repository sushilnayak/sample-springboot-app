package com.nayak.samplespringbootapp.controller;

import com.nayak.samplespringbootapp.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void basicTest(){
        Student[] students = restTemplate.getForObject("/api/v1/students", Student[].class);
//        Student(id=1, name=john, age=11, height=123.33, weight=40.22)
//        Student(id=2, name=eva, age=10, height=123.33, weight=35.11)
        assertThat( students ).hasSize(2);
//        for(Student student : students){
//            System.out.println(student);
//        }
    }
}
