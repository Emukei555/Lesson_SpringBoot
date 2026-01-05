package com.sqlcanvas.lesson_spring_boot3.service;

import com.sqlcanvas.lesson_spring_boot3.entity.User;
import com.sqlcanvas.lesson_spring_boot3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // --- 実際の業務処理（メソッド） ---

    /**
     * ユーザーを登録する処理
     * @param name 名前
     * @param email メールアドレス
     * @return 登録されたユーザー情報
     */
    public User registerUser(String name, String email) {
        var newUser = new User(name, email);
        return userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * ユーザー情報を更新する
     * @param id 更新したいユーザーのID
     * @param name 新しい名前
     * @param email 新しいメールアドレス
     * @return 更新後のユーザー情報
     */
    public User updateUser(Long id, String name, String email){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません ID: " + id));

        // 2. データを書き換える
        user.setName(name);
        user.setEmail(email);

        return userRepository.save(user);
    }

    /**
     * ユーザーを削除する
     * @param id 削除したいユーザーのID
     */
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("ユーザーが見つかりません ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
