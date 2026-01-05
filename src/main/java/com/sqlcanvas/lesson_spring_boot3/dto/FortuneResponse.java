package com.sqlcanvas.lesson_spring_boot3.dto;

import lombok.Builder; // ← インポート
import lombok.Data;

@Data
@Builder // ← これをつけるだけ！
public class FortuneResponse {
    private String result;
    private String comment;
}