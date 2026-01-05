package com.sqlcanvas.lesson_spring_boot3.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class FortuneHistoryResponse {
    private long id;
    private String result;
    private String comment;
    private LocalDateTime createdAt; // ← 履歴には日付が欲しいですよね
}
