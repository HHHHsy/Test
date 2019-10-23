package com.gzy.im.controller;

import com.gzy.im.entity.Token;
import com.gzy.im.entity.User;
import com.gzy.im.repository.TokenRepository;
import com.gzy.im.repository.UserRepository;
import com.gzy.im.server.TestServer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class TestController {

    @Resource
    TestServer testServer;

    @Resource
    UserRepository userRepository;

    @Resource
    TokenRepository tokenRepository;

    //    访问 api 可以这样访问
    //    http://localhost:8080/register?username=ffff11111222&password=123456
    @RequestMapping("/register")
    public Object test(String username,String password){

        String name = username;
        User user = new User();

        user.setName(name);
        user.setPassword(password);

        User user1 = userRepository.save(user);

        // 返回数据怎么转化成 json

        return user1;
    }

    @PostMapping("/login")
    public Object login(String username,String password){
        if (username == null && username == ""){
            return ResponseEntity.badRequest().body("请输入用户名称");
        }
        if (password == null && password == ""){
            return ResponseEntity.badRequest().body("请输入密码");
        }

        User user = userRepository.findUserByName(username);
        if (user == null){
            return ResponseEntity.badRequest().body("用户未找到");
        }

        if (password.equals(user.getPassword())) {


            Token token = new Token();
            token.setUserid(user.getId());
            token.setToken(UUID.randomUUID().toString());

            tokenRepository.save(token);

            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(403).body("密码不正确");
    }
    }


