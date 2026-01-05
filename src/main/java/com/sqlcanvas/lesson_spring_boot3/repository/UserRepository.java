package com.sqlcanvas.lesson_spring_boot3.repository;

import com.sqlcanvas.lesson_spring_boot3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}