package com.example.web.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Course {
    Integer courseId;
    String courseName;
    Long userid;
    String courseInf;
    Double courseScore;
}
