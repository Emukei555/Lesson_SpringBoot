package com.sqlcanvas.lesson_spring_boot3.repository;

import com.sqlcanvas.lesson_spring_boot3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // このクラスがRepositoryであることを示す
public interface UserRepository extends JpaRepository<User, Long> {
    // ここには何も書かなくてOKです！
    // JpaRepository を継承しただけで、最強の武器（CRUD操作）がすべて手に入りました。
}