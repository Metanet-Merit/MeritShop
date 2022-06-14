package com.merit.meritShop.user.repository;

import com.merit.meritShop.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(@Param(value = "email") String email);
    boolean existsByEmail(String email);
    boolean existsByLoginType(String loginType);
    Optional<User> findByEmailAndLoginType(String email, String loginType);

    Page<User> findByUserNameContaining(String keyword, Pageable pageable);
}
