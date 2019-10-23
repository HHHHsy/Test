package com.gzy.im.repository;

import com.gzy.im.entity.Token;
import com.gzy.im.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findTokenByToken(String token);
}
