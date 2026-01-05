package com.sqlcanvas.lesson_spring_boot3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FortuneRequest {

    @NotBlank(message = "運勢は必須です")
    private String result;

    @NotBlank(message = "コメントは必須です")
    @Size(max = 100, message = "コメントは100文字以内で書いてください")
    private String comment;
}
