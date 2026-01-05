package com.sqlcanvas.lesson_spring_boot3.service;

import com.sqlcanvas.lesson_spring_boot3.dto.FortuneResponse;
import com.sqlcanvas.lesson_spring_boot3.entity.FortuneResult; // ← Entityを追加
import com.sqlcanvas.lesson_spring_boot3.repository.FortuneRepository; // ← Repositoryを追加
import com.sqlcanvas.lesson_spring_boot3.dto.FortuneRequest;
import lombok.RequiredArgsConstructor; // ← これを追加！(DI用)
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ← トランザクション

import com.sqlcanvas.lesson_spring_boot3.dto.FortuneHistoryResponse;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // ← finalがついた変数を自動で準備してくれる！
public class FortuneService {

    // 保存係を呼び出す準備
    private final FortuneRepository fortuneRepository;

    @Transactional // ← DBを変更する時はこれをつけるのが鉄則
    public FortuneResponse drawFortune() {
        var random = new Random();
        int num = random.nextInt(10);

        String resultStr = "";
        String commentStr = "";

        if (num == 0) {
            resultStr = "大吉";
            commentStr = "今日は最高の日になりそう！";
        } else if (num >= 1 && num <= 3) {
            resultStr = "中吉";
            commentStr = "いいことあるかも。";
        } else if (num >= 4 && num <= 7) {
            resultStr = "吉";
            commentStr = "普通が一番幸せです。";
        } else {
            resultStr = "凶";
            commentStr = "足元に気をつけて...";
        }

        // --- ここから追加：DBへの保存処理 ---

        // 1. 保存用のEntityを作る（入れ替え作業）
        FortuneResult dbEntity = new FortuneResult();
        dbEntity.setResult(resultStr);
        dbEntity.setComment(commentStr);
        dbEntity.setCreatedAt(LocalDateTime.now()); // 現在時刻


        // 2. Repositoryを使って保存！
        fortuneRepository.save(dbEntity);

        log.info("DBに保存しました: {}", dbEntity);
        // ----------------------------------

        return FortuneResponse.builder()
                .result(resultStr)
                .comment(commentStr)
                .build();
    }

    public List<FortuneHistoryResponse> getHistory() {
        List<FortuneResult> entities = fortuneRepository.findAll();
        // 共通メソッドを呼ぶ
        return convertToDtoList(entities);
    }

    public List<FortuneHistoryResponse> getHistoryByResult(String searchResult) {
        List<FortuneResult> entities = fortuneRepository.findByResult(searchResult);
        // 共通メソッドを呼ぶ
        return convertToDtoList(entities);
    }

    // 【共通化】Entityのリストを受け取って、DTOのリストを返すメソッド
    private List<FortuneHistoryResponse> convertToDtoList(List<FortuneResult> entities) {
        return entities.stream()
                .map(entity -> FortuneHistoryResponse.builder()
                        .id(entity.getId())
                        .result(entity.getResult())
                        .comment(entity.getComment())
                        .createdAt(entity.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // IDを指定してデータを消す機能
    public void delete(Long id) {
        // Repositoryにある魔法のメソッドを呼ぶだけ！
        fortuneRepository.deleteById(id);
        log.info("ID: {} のデータを削除しました", id);
    }

    public void update(Long id, FortuneRequest request) {

        FortuneResult entity = fortuneRepository.findById(id).orElse(null);

        if(entity != null) {
            entity.setResult(request.getResult());
            entity.setComment(request.getComment()); // 新しいコメントをセット

            // 3. 上書き保存！
            // 実はJPAの save() は、IDが既に存在する場合は「更新」として動きます
            fortuneRepository.save(entity);
        }
    }
}