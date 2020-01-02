package com.sini.doneit.controller;


import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.UserJpaRepository;
import com.sini.doneit.services.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.sini.doneit.model.MessageCode.*;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    LoginValidator loginValidator;

    @Autowired
    UserJpaRepository userRepository;

    @PostMapping(path = "/authenticate-user")
    public ResponseEntity<ResponseMessage> authenticateUser(@RequestBody User user) {
        User tmpUser = null;
        ResponseMessage responseMessage = null;
        if (loginValidator.validateLogin(user)) {
            tmpUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (tmpUser != null) {
                responseMessage = new ResponseMessage("Login Effettuato con Successo", SUCCESSFUL_LOGIN);
            }
            else{
                responseMessage = new ResponseMessage("Credenziali errate", INVALID_CREDENTIAL);
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        } else {
            responseMessage = new ResponseMessage("Dati corrotti", INVALID_DATA);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
