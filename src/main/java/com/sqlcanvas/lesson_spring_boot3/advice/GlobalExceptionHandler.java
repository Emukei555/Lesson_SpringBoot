package com.sqlcanvas.lesson_spring_boot3.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// アプリ全体で起きた例外（エラー）をキャッチするクラス
@RestControllerAdvice
public class GlobalExceptionHandler {

    // "バリデーションエラー(MethodArgumentNotValidException)" が起きたら、このメソッドが動く
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // エラー内容を詰め込むための箱（Map）を作る
        Map<String, String> errors = new HashMap<>();

        // 発生したエラーを1つずつ取り出して、箱に入れる
        // 例: "result" -> "運勢は必須です"
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // どの項目で？
            String errorMessage = error.getDefaultMessage();    // どんなエラー？
            errors.put(fieldName, errorMessage);
        });

        // 400 Bad Request として返す
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}