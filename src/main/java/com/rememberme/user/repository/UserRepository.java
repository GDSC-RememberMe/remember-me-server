package com.rememberme.user.repository;

import com.rememberme.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByPhone(String phone);

    List<Member> findByUsernameContaining (String keyword);
}