package com.gzy.im.controller;


import com.gzy.im.entity.Token;
import com.gzy.im.entity.User;
import com.gzy.im.repository.TokenRepository;
import com.gzy.im.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class UserController {

    @Resource
    TokenRepository tokenRepository;

    @Resource
    UserRepository userRepository;

    @GetMapping("/userinfo")
    Object userinfo(Long userid,String token){
        Token tokenByToken =  tokenRepository.findTokenByToken(token);

        if (tokenByToken==null) {
            return ResponseEntity.status(401).build();
        }
        if (!tokenByToken.getId().equals(userid)) {
            return ResponseEntity.status(401).build();
        }
        Optional<User> userOptional = userRepository.findById(userid);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return  ResponseEntity.notFound().build();

    }
}
