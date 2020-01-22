package com.sini.doneit.controller;

import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.PersonalCard;
import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.PersonalCardJpaRepository;
import com.sini.doneit.repository.UserJpaRepository;
import com.sini.doneit.services.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.sini.doneit.model.MessageCode.*;

@RestController
@CrossOrigin("*")
public class RegisterController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RegisterValidator registerValidator;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PersonalCardJpaRepository personalCardJpaRepository;

    @PostMapping(path = "/register-user")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody User user) {
        ResponseMessage responseMessage = null;
        if (registerValidator.validateRegister(user)) {
            if(userAlreadyExists(user)){
                responseMessage = new ResponseMessage("Username e/o mail già esistenti!", USER_ALREADY_CREATED);
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }
            encryptPassword(user);
            user.setPersonalCard(new PersonalCard().setUser(user));
            userJpaRepository.save(user);
            responseMessage = new ResponseMessage("Utente creato con successo", SUCCESSFUL_REGISTER);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        System.out.println("credenziali non valide");
        responseMessage = new ResponseMessage("Errore nella creazione dell'utente", INVALID_DATA);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    private void encryptPassword(User user){
        String password = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
    }


    @PostMapping("/complete-register")
    public ResponseEntity<ResponseMessage> completeRegister(@RequestHeader HttpHeaders httpHeaders,
                                                            @RequestBody PersonalCard personalCard){
        System.out.println(personalCard);
        return null;

    }

    private boolean userAlreadyExists(User user){
        return userJpaRepository.findByUsername(user.getUsername()) != null ||
                userJpaRepository.findByEmail(user.getEmail()) != null;
    }


}
