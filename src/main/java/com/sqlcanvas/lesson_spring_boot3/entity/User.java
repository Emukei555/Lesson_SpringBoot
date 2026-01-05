package com.sqlcanvas.lesson_spring_boot3.entity;

import jakarta.persistence.*;
import lombok.Data; // Lombokをインポート
import lombok.NoArgsConstructor; // 引数なしコンストラクタ用

@Entity
@Table(name = "users")
@Data // ① これだけで Getter, Setter, toString, hashCode 等を全部自動生成！
@NoArgsConstructor // ② JPAに必要な「空のコンストラクタ」を自動生成！
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // ★Serviceで「new User(name, email)」を使っているため、
    // この「便利コンストラクタ」だけは手動で残しておきます。
    // （これもLombokの @AllArgsConstructor などで代用できますが、今回は分かりやすさ優先で残します）
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
