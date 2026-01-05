package com.sqlcanvas.lesson_spring_boot3.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "fortune_results")
public class FortuneResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 結果（大吉など）
    private String result;

    // コメント
    private String comment;

    // 引いた日時（これがあると便利！）
    private LocalDateTime createdAt;
}