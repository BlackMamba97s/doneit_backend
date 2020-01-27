package com.sini.doneit.controller;

import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.PersonalCard;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.PersonalCardJpaRepository;
import com.sini.doneit.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PersonalCardJpaRepository personalCardJpaRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/my-personal-card")
    public PersonalCard getPersonalCard(@RequestHeader HttpHeaders httpHeaders) {
        User user = userJpaRepository.findByUsername
                (jwtTokenUtil.getUsernameFromHeader(httpHeaders));
        return user.getPersonalCard();
    }

    @GetMapping("/user-personal-card/{username}")
    public PersonalCard getUserPersonalCard(@PathVariable String username) {
        User user = userJpaRepository.findByUsername(username);
        if (user == null) {
            System.out.println("Errore richiesta");
            return null;
        }
        return user.getPersonalCard();
    }
}
