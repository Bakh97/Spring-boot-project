package com.deep.Ecomm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.deep.Ecomm.Repository.UserRepo;
import com.deep.Ecomm.model.User;

@Service
public class UserService {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo repo;
    private BCryptPasswordEncoder bcrypt =new BCryptPasswordEncoder(12);
    public User registerUser(User user){
        user.setPassword(bcrypt.encode(user.getPassword()));
        repo.save(user);
        return user;
    }
    public String verfiyUser(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.getToken(user.getUsername());
        }
        return "fail";
    }
}
