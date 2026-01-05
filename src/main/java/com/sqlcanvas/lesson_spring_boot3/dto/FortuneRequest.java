package com.sqlcanvas.lesson_spring_boot3.dto;

import lombok.Data;

@Data
public class FortuneRequest {
    private String result;
    private String comment;
}
