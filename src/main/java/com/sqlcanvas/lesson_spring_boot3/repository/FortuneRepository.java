package com.sqlcanvas.lesson_spring_boot3.repository;

import com.sqlcanvas.lesson_spring_boot3.entity.FortuneResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FortuneRepository extends JpaRepository<FortuneResult, Long> {
    // これだけで save() が使えるようになります！
    List<FortuneResult> findByResult(String result);
}