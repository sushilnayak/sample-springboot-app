package com.nayak.samplespringbootapp.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    @Size(min = 3, max = 20)
    String name;

    @Positive
    int age;

    @Positive
    float height;

    @Positive
    float weight;
}
