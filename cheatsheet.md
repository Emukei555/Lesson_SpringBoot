# ☕️ Modern Java (Spring Boot 4 / Java 21) Cheat Sheet

Java 21とSpring Boot 4によるREST API開発のための、実務的な書き方まとめです。

---

## 🚀 1. データの入れ物 (DTO) - Record
データの受け渡し用クラスは `class` ではなく **`record`** を使います。
Lombok不要で、不変（Immutable）なクラスが1行で定義できます。

### 定義方法
```java
// リクエスト用DTO (POSTで受け取るデータ)
public record UserRequest(
    String name,
    String email,
    Integer age
) {}

// レスポンス用DTO (JSONとして返すデータ)
public record UserResponse(
    Long id,
    String name,
    String status
) {}
```

### 使い方
```java
var req = new UserRequest("山田", "test@example.com", 20);

// Getterは getXxx() ではなく xxx()
String n = req.name(); 
String e = req.email();
```

---

## 🌊 2. Stream API - コレクション操作
List や Set を for 文で回さず、「宣言的」に処理します。

### 基本構文
```java
List<String> results = sourceList.stream() // 1. Stream化
    .filter( item -> /* 条件 */ )          // 2. 中間操作 (抽出)
    .map( item -> /* 変換 */ )             // 3. 中間操作 (加工)
    .sorted( /* 並び替え */ )              // 4. 中間操作 (整列)
    .toList();                             // 5. 終端操作 (リスト化: Java 16+)
```

### よく使う操作一覧
| やりたいこと | メソッド | コード例 |
|------------|---------|---------|
| 抽出 (〜だけ残す) | filter | `.filter(u -> u.getAge() >= 20)` |
| 変換 (AをBに変える) | map | `.map(User::getName)` |
| 並べ替え (昇順) | sorted | `.sorted(Comparator.comparing(User::getAge))` |
| 並べ替え (降順) | sorted | `.sorted(Comparator.comparing(User::getAge).reversed())` |
| 個数カウント | count | `long count = stream.count();` |
| 重複排除 | distinct | `.distinct()` |
| 判定 (1つでもある?) | anyMatch | `.anyMatch(u -> u.isActive())` |
| 数値合計 | mapToInt | `.mapToInt(Order::getPrice).sum()` |

---

## 🛡️ 3. Optional - Null安全な検索
findById など、結果が無いかもしれない場合は Optional で受け取ります。

### 実務の鉄板パターン (orElseThrow)
データがない場合に即座に404エラーなどを返すパターンです。
```java
User user = repository.findById(id)
    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "見つかりません"));
```

### その他のパターン
```java
// デフォルト値を使う
User user = repository.findById(id).orElse(new User("ゲスト"));

// 値がある時だけ処理する (戻り値なし)
repository.findById(id).ifPresent(u -> {
    System.out.println("発見: " + u.getName());
});
```

---

## 📦 4. Entity (JPA) - Lombokの正解
DB用のクラスには record は使えません。 Lombokを使いますが、@Data は使用禁止です。

### Entityのテンプレート
```java
@Entity
@Table(name = "users")
@Getter             // ⭕️ 読み取りOK
@Setter             // ⭕️ 書き込みOK
@NoArgsConstructor  // ⭕️ JPAの必須要件
@AllArgsConstructor // ⭕️ テスト等で便利
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private LocalDateTime createdAt;
}
```

---

## 🚦 5. Switch式 (Java 21)
値を返すSwitchです。break 不要で、アロー演算子 -> を使います。

### 基本形
```java
String message = switch (status) {
    case DONE    -> "完了しました";
    case DOING   -> "作業中です";
    case TODO    -> "未着手です";
    default      -> "不明なステータス";
};
```

### ガード節 (when) - Java 21新機能
```java
String type = switch (score) {
    case int i when i >= 90 -> "Sランク";
    case int i when i >= 70 -> "Aランク";
    default -> "Bランク以下";
};
```

---

## ✨ 6. Java 21 便利機能

### Listの最初と最後
```java
var list = List.of("A", "B", "C");

String first = list.getFirst(); // "A" (昔は list.get(0))
String last  = list.getLast();  // "C" (昔は list.get(list.size() - 1))
```

### 無名変数 (_)
使わない変数は _ で省略できます。
```java
try {
    service.exec();
} catch (Exception _) { // "e" と書かなくていい
    System.out.println("エラーは無視");
}
```

---

## 🏗️ 7. Service層の実装テンプレート
ロジックを書くときの基本形です。
```java
@Service
@Transactional // 更新系を含むなら必須
public class UserService {
    private final UserRepository repository;

    // コンストラクタ注入 (Spring推奨)
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * 全ユーザーを名前順で取得し、DTOに変換して返す
     */
    public List<UserResponse> getAll() {
        return repository.findAll().stream()
            .sorted(Comparator.comparing(User::getName))
            .map(e -> new UserResponse(e.getId(), e.getName())) // Entity -> Record
            .toList();
    }
}
```