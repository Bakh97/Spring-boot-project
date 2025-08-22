package com.deep.Ecomm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.Ecomm.model.User;
import com.deep.Ecomm.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
            return userService.registerUser(user);
    }

    @PostMapping("/login")
    public  String  login(@RequestBody User user){
        return userService.verfiyUser(user);
    }
}
