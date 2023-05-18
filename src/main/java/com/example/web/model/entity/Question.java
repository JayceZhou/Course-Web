package com.example.web.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Question {
    Integer questionid;
    Integer courseid;
    Long userid;
    String title;
    String content;
    Timestamp time;
    Integer answerCheck;
    Integer privateCheck;
    Integer markCheck;
}
