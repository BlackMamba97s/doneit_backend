package com.sini.doneit.controller;


import com.sini.doneit.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {


    @PostMapping(path = "/authenticate-user")
    public ResponseEntity<Void> authenticateUser(@RequestBody User user){
        System.out.println(user);
        return ResponseEntity.ok().build();
    }

}
