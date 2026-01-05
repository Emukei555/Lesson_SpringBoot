package com.sqlcanvas.lesson_spring_boot3.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.concurrent.locks.StampedLock;

// Java 14以降で使える「record」。
// Getter, Setter, コンストラクタなどを全部自動で作ってくれる「データ運び専用」の箱です。
public record UserRequest(
        @NotBlank(message = "名前は必須です") // ① 空文字やnullを許さない
        String name,

        @NotBlank(message = "メールアドレスは必須です")
        @Email(message = "メールアドレスの形式が正しくありません") // ② "test" みたいな文字列を許さない
        String email
) {
}