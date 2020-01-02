package com.sini.doneit.services;


import com.sini.doneit.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginValidator {

    public boolean validateLogin(User user){
        return validateUsername(user.getUsername()) && validatePassword(user.getPassword());
    }

    private boolean validateUsername(String username){
        return username.length() > 4 && username.length() < 16;
    }

    private boolean validatePassword(String password){
        return password.length() > 8 && password.length() < 30;
    }
}
