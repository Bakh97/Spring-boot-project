package com.deep.Ecomm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deep.Ecomm.model.User;
import com.deep.Ecomm.service.MyUserDetailsService;

@Repository
public interface UserRepo extends JpaRepository <User,Integer>{

    User findByUsername(String username);
    
}
