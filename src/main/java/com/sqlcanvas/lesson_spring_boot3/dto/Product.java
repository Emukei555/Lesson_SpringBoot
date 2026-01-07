package com.sqlcanvas.lesson_spring_boot3.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Product(
        @NotBlank(message = "名前は必須です")
        String name,

        @Min(value = 0, message = "0以上である必要があります")
        int price,

        String category) {
    public boolean isHighPrice() {
        return price >= 100000;
    }
}
