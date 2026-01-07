package com.sqlcanvas.lesson_spring_boot3.service;

import com.sqlcanvas.lesson_spring_boot3.dto.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    // Recordのコンストラクタ呼び出しは class と同じでOK
    private final List<Product> products = List.of(
            new Product("MacBook", 200000, "PC"),
            new Product("iPhone", 120000, "PHONE"),
            new Product("Pixel", 90000, "PHONE"),
            new Product("Coffee", 500, "FOOD"),
            new Product("Bread", 200, "FOOD")
    );

    /**
     * 【練習1】フィルタリング、ソート、変換
     * Recordなので .price() .name() になっています (OK!)
     */
    public List<String> findProductNamesByCategory(String category) {
        return products.stream()
                // .getCategory() ではなく .category()
                .filter(product -> product.category().equalsIgnoreCase(category))
                // Product::getPrice ではなく Product::price
                .sorted(Comparator.comparing(Product::price))
                // Product::getName ではなく Product::name
                .map(Product::name)
                .toList();
    }

    public int analyzePrice(String category) {
        return products.stream()
                .filter(product -> product.category().equalsIgnoreCase(category))
                .mapToInt(Product::price)
                .sum();
    }

    public List<String> findHighPrice() {
        return products.stream()
                .filter(Product::isHighPrice)
                .map(Product::name)
                .toList();
    }

    /**
     * FOODの合計金額
     */
    public int getFoodTotalPrice() {
        return products.stream()
                // 修正1: 引数がないので直接 "FOOD" を指定
                .filter(product -> product.category().equalsIgnoreCase("FOOD"))
                // 修正2: Product::getPrice → Product::price (getを取る！)
                .mapToInt(Product::price)
                .sum();
    }

    /**
     * PHONEの名前を大文字で
     */
    public List<String> getUpperPhoneNames() {
        return products.stream()
                // 修正1: 引数がないので直接 "PHONE" を指定 (スペルミス categor も修正)
                .filter(product -> product.category().equalsIgnoreCase("PHONE"))
                // 修正2: Product::getName → Product::name (getを取る！)
                .map(Product::name)
                .map(String::toUpperCase)
                .toList();
    }
}