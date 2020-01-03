package com.sini.doneit.controller;

import com.sini.doneit.model.ResponseMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class HelloController {

    @GetMapping("/hello")
    public ResponseMessage hello(){
        return new ResponseMessage("Ciao bello", 1);
    }
}
