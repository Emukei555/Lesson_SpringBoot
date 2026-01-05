package com.sqlcanvas.lesson_spring_boot3.controller;

import com.sqlcanvas.lesson_spring_boot3.dto.UserRequest;
import com.sqlcanvas.lesson_spring_boot3.entity.User;
import com.sqlcanvas.lesson_spring_boot3.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // ① ここがポイント！「APIを作るコントローラーですよ」という宣言
@RequestMapping("/users") // ② このコントローラーは "http://localhost:8080/users" へのアクセスを担当します
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // --- 注文を受ける窓口（エンドポイント） ---

    /**
     * ユーザー登録API
     * URL: POST http://localhost:8080/users/register
     * パラメータ: name, email
     */
    @PostMapping("/register")
    // @RequestBody: 「URLの横」ではなく「リクエストの本文（Body）」にあるJSONを受け取ります
    public User register(@RequestBody @Valid UserRequest request) {
        // 注文票（DTO）からデータを取り出して、シェフ（Service）に渡す
        return userService.registerUser(request.name(), request.email());
    }

    /**
     * ユーザー全件取得API
     * URL: GET http://localhost:8080/users
     */
    @GetMapping // ④ "GET" メソッドで "/users" に来たらここが動く
    public List<User> getAllUsers() {
        // シェフ（Service）に「全員分の料理出して」と伝える
        return userService.getAllUsers();
    }

    /**
     * ユーザー更新API
     * URL: PUT http://localhost:8080/users/{id}
     * Body: JSON
     */
    @PutMapping("/{id}") // ① 更新は "PUT" メソッドを使うのがルール
    public User update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        // @PathVariable: URLの "{id}" の部分（数字）を受け取るアノテーション
        return userService.updateUser(id, request.name(), request.email());
    }

    /**
     * ユーザー削除API
     * URL: DELETE http://localhost:8080/users/{id}
     */
    @DeleteMapping("/{id}") // ② 削除は "DELETE" メソッドを使う
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "ユーザーを削除しました ID: " + id;
    }
}

