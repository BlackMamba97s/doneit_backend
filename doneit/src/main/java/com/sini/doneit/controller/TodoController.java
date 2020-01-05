package com.sini.doneit.controller;


import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.Todo;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.TodoJpaRepository;
import com.sini.doneit.repository.UserJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sini.doneit.model.MessageCode.*;

@RestController
@CrossOrigin("*")
public class TodoController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @PostMapping("/create-todo")
    public ResponseEntity<ResponseMessage> createTodo(@RequestBody Todo todo, @RequestHeader HttpHeaders headers) {
        String token = jwtTokenUtil.getTokenFromHeader(headers);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userJpaRepository.findByUsername(username);
        todo.setUser(user);
        todoJpaRepository.save(todo);
        return new ResponseEntity<>(new ResponseMessage("Todo creato correttamente", TODO_CREATED),
                HttpStatus.OK);
    }

    @GetMapping("/get-todo-list")
    public List<Todo> getUserTodoList(@RequestHeader HttpHeaders headers) {
        String username = jwtTokenUtil.getUsernameFromToken((jwtTokenUtil.getTokenFromHeader(headers)));
        List<Todo> todoList = userJpaRepository.findByUsername(username).getTodoList();
        return todoList;
    }

    @DeleteMapping("/delete-todo/{todoId}")
    public ResponseEntity<ResponseMessage> deleteTodo(@RequestHeader HttpHeaders headers, @PathVariable Long todoId) {
        User user = userJpaRepository.findByUsername(jwtTokenUtil.getUsernameFromHeader(headers));
        Optional<Todo> todo = todoJpaRepository.findById(todoId);
        if (todo.isPresent()) {
            if (user.isOwnerOfTodo(todo.get())) {
                user.removeTodo(todo.get());
                todoJpaRepository.deleteById(todoId);
                return new ResponseEntity<>(new ResponseMessage("Todo eliminato con successo", TODO_DELETED),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ResponseMessage("Errore nell'eliminazione del todo", ERROR_TODO_REMOVAL),
                HttpStatus.UNAUTHORIZED);
    }

}
