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
public class Answer {
    Integer questionid;
    Integer answerid;
    String anscontent;
    Timestamp anstime;
}
