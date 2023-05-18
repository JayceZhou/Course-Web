package com.example.web.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Teacher {
    Long userid;
    String teacherName;
    String teacherTitle;
    String teacherInf;
}
