package com.sqlcanvas.lesson_spring_boot3.service;

import com.sqlcanvas.lesson_spring_boot3.dto.ExamRequest;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    public String judge(ExamRequest request) {
        // 変数準備：リクエストから点数と名前を取り出して、扱いやすくしておく
        int score = request.getScore();
        String name = request.getName();
        String resultMessage; // 結果を入れる箱
        // 1. バリデーション：点数が「0未満」または「100より大きい」場合
        //    → 「エラー：正しい点数を入力してください」と返して終わる
        if (score < 0 || score > 100) {
            return "エラー：0〜100の間で入力してください。";
        }
        // 2. 判定ロジック：点数に応じてメッセージを変える
        //    - 80点以上 → 「素晴らしい！S評価です」
        //    - 60点以上 → 「おめでとうございます！合格です」
        //    - それ以外 → 「残念...不合格（追試）です」
        if (score >= 80) {
            resultMessage = "素晴らしい！S評価です";
        } else if (score >= 60) {
            resultMessage = "おめでとうございます！合格です";
        } else {
            resultMessage = "残念...不合格（追試）です";
        }
        // 3. 最終的なメッセージ：名前と判定結果を組み合わせて返す
        return name + "さんの結果：" + resultMessage;
    }
}