package com.nayak.samplespringbootapp.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    long id;
    String name;
    int age;
    float height;
    float weight;
}
