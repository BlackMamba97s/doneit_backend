package com.sini.doneit.controller;


import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.Todo;
import com.sini.doneit.repository.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.sini.doneit.model.MessageCode.TODO_CREATED;

@RestController
@CrossOrigin("*")
public class TodoController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @PostMapping("/create-todo")
    public ResponseEntity<ResponseMessage> createTodo(@RequestBody Todo todo, @RequestHeader HttpHeaders headers) {

        String token = jwtTokenUtil.getTokenFromHeader(headers);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println(username);
        todoJpaRepository.save(todo);
        return new ResponseEntity<>(new ResponseMessage("Todo creato correttamente", TODO_CREATED),
                HttpStatus.OK);
    }
}
