package com.sini.doneit.services;


import com.sini.doneit.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginValidator {

    public boolean validateLogin(User user){
        return validateUsername(user.getUsername()) && validatePassword(user.getPassword());
    }

    private boolean validateUsername(String username){
        return username.length() > 2;
    }

    private boolean validatePassword(String password){
        return password.length() > 2;
    }
}
