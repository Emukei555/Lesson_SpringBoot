package com.sqlcanvas.lesson_spring_boot3.controller;


import com.sqlcanvas.lesson_spring_boot3.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    // 呼び出し例: GET /api/products/search?category=PHONE
    @GetMapping("/search")
    public List<String> search(@RequestParam @Valid String category) {
        return service.findProductNamesByCategory(category);
    }

    // 呼び出し例: GET /api/products/stats?category=PHONE
    @GetMapping("/stats")
    public int getStats(@RequestParam @Valid String category) {
        // 結果: {count=2, sum=210000, min=90000, average=105000.00...} が返る
        return service.analyzePrice(category);
    }
}