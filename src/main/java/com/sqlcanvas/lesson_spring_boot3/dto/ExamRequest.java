package com.sqlcanvas.lesson_spring_boot3.dto;

import lombok.Data;

@Data
public class ExamRequest {
    // 名前
    private String name;
    // 点数
    private Integer score;
}