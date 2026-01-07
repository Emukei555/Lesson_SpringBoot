package com.sqlcanvas.lesson_spring_boot3.dto;

public record Product(String name, int price, String category) {
    public boolean isHighPrice() {
        return price >= 100000;
    }
}
