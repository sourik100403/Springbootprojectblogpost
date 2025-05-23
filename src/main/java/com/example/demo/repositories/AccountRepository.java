package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
    Optional<Account> findByEmailIgnoreCase(String email);
    // Optional<Account> findByToken(String token);
    // Optional<Account> findByPassword_Reset_Token(String token);
    Optional<Account> findByPasswordResetToken(String token);

}

