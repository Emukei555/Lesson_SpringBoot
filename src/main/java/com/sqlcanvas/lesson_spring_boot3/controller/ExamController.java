package com.sqlcanvas.lesson_spring_boot3.controller;

import com.sqlcanvas.lesson_spring_boot3.dto.ExamRequest;
import com.sqlcanvas.lesson_spring_boot3.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor // コンストラクタ注入（Lombok）
public class ExamController {
    private ExamService examService;

    @PostMapping("/judge")
    public String judgeExam(@RequestBody ExamRequest request) {
        // 1. Service（シェフ）に、受け取ったデータを渡して判定してもらう
        String response = examService.judge(request);

        // 2. 結果をユーザーに返す
        return response;
    }
}
