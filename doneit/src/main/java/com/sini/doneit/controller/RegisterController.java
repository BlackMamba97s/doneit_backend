package com.sini.doneit.controller;

import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.UserJpaRepository;
import com.sini.doneit.services.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sini.doneit.model.MessageCode.*;

@RestController
@CrossOrigin("*")
public class RegisterController {

    @Autowired
    LoginValidator loginValidator;

    @Autowired
    UserJpaRepository userJpaRepository;

    @PostMapping(path = "/register-user")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody User user) {
        ResponseMessage responseMessage = null;
        if (loginValidator.validateLogin(user)) {
            encryptPassword(user);
            userJpaRepository.save(user);
            responseMessage = new ResponseMessage("Utente creato con successo", SUCCESSFUL_REGISTER);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        responseMessage = new ResponseMessage("Errore nella creazione dell'utente", INVALID_DATA);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    private void encryptPassword(User user){
        String password = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
    }
}
