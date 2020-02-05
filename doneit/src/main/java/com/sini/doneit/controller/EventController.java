package com.sini.doneit.controller;

import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.Event;
import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.EventJpaRepository;
import com.sini.doneit.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sini.doneit.model.MessageCode.*;

@RestController
@CrossOrigin("*")
public class EventController {
    @Autowired
    EventJpaRepository eventJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(path = "create-event")
    public ResponseEntity<ResponseMessage> createEvent(@RequestBody Event event, @RequestHeader HttpHeaders headers){
        User owner = userJpaRepository.findByUsername(jwtTokenUtil.getUsernameFromHeader(headers));
        event.setUser(owner);
        eventJpaRepository.save(event);

        return new ResponseEntity<>(new ResponseMessage("Evento creato correttamente", EVENT_CREATED),
                HttpStatus.OK);
    }

    @GetMapping(path = "active-event-list")
    public List<Event> getActiveEvents(@RequestHeader HttpHeaders httpHeaders){
        User owner = userJpaRepository.findByUsername(jwtTokenUtil.getUsernameFromHeader(httpHeaders));
        List<Event> eventList = eventJpaRepository.findEventList();

        return eventList;
    }

    @GetMapping(path = "event-list/users/{username}")
    public List<Event> getEventListByUsername(@PathVariable String username){
        User user = this.userJpaRepository.findByUsername((username));
        return this.eventJpaRepository.findByUser(user);
    }
}
