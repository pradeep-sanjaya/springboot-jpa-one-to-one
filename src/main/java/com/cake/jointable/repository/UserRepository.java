package com.cake.jointable.repository;

import com.cake.jointable.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User getById(Long id);
}
