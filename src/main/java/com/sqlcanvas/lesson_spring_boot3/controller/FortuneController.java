package com.sqlcanvas.lesson_spring_boot3.controller;

import com.sqlcanvas.lesson_spring_boot3.dto.FortuneHistoryResponse;
import com.sqlcanvas.lesson_spring_boot3.dto.FortuneRequest;
import com.sqlcanvas.lesson_spring_boot3.dto.FortuneResponse;
import com.sqlcanvas.lesson_spring_boot3.entity.FortuneResult;
import com.sqlcanvas.lesson_spring_boot3.service.FortuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FortuneController {

    private final FortuneService fortuneService;

    @GetMapping("/fortune")
    public FortuneResponse play() { // ← 戻り値を String から FortuneResponse に変えるだけ！
        return fortuneService.drawFortune();
    }

    @GetMapping("/fortune/history")
    public List<FortuneHistoryResponse> getHistory(@RequestParam(required = false) String result) {

        // これで result が使えるようになります
        if (result != null) {
            return fortuneService.getHistoryByResult(result);
        } else {
            return fortuneService.getHistory();
        }
    }

    // DELETE メソッドで /fortune/history/{id} に来たら動く
    // {id} の部分が、引数の id に入ります
    @DeleteMapping("/fortune/history/{id}")
    public void delete(@PathVariable Long id) {
        fortuneService.delete(id);
    }
    @PutMapping("/fortune/history/{id}")
    public void update(@PathVariable Long id, @RequestBody FortuneRequest request) {
        // @RequestBody は「送られてきたJSONをこのクラス(request)に入れてね」という指示です
        fortuneService.update(id, request);
    }
}
