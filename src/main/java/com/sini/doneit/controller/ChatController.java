package com.sini.doneit.controller;

import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.MessageChat;
import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.User;
import com.sini.doneit.model.dto.Greeting;
import com.sini.doneit.model.dto.HelloMessage;
import com.sini.doneit.model.dto.SocketChatMessage;
import com.sini.doneit.repository.ChatMessageJpaRepository;
import com.sini.doneit.repository.UserJpaRepository;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.Date;

@RestController
@CrossOrigin("*")

public class ChatController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ChatMessageJpaRepository chatMessageJpaRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/send")
    public void send(Principal principal, SocketChatMessage socketChatMessage) throws Exception {
        System.out.println("ricevuto messaggio! Devo inviarlo a " + socketChatMessage.getUserTo());
        System.out.println(socketChatMessage);
        if (socketChatMessage.getContent().equals("") || socketChatMessage.getUserTo() == null) {
            return;
        }
        socketChatMessage.setUserFrom(principal.getName());
        saveMessage(socketChatMessage);
        messagingTemplate.convertAndSendToUser(socketChatMessage.getUserTo(), "/queue/reply", socketChatMessage);
    }

    @GetMapping("/chat/get-conversation/{user}")
    public List<MessageChat> getMessageFromUser(@PathVariable String user,
                                                @RequestHeader HttpHeaders httpHeaders) {
        User to = userJpaRepository.findByUsername(jwtTokenUtil.getUsernameFromHeader(httpHeaders));
        User from = userJpaRepository.findByUsername(user);
        List<MessageChat> conversation =
                chatMessageJpaRepository.getConversationByUser(from.getId(), to.getId());
        System.out.println(conversation);
        return conversation;
    }

    private void saveMessage(SocketChatMessage socketChatMessage) {
        User from = userJpaRepository.findByUsername(socketChatMessage.getUserFrom());
        User to = userJpaRepository.findByUsername(socketChatMessage.getUserTo());

        MessageChat messageChat = new MessageChat();
        messageChat.setDate(new Date());
        messageChat.setTo(to);
        messageChat.setFrom(from);
        messageChat.setContent(socketChatMessage.getContent());
        chatMessageJpaRepository.save(messageChat);
    }
}


//    @PostMapping("/chat/send-message")
//    public ResponseEntity<ResponseMessage> sendMessage(@RequestBody MessageChat messageChat,
//                                                       @RequestHeader HttpHeaders httpHeaders) {
//        User from = userJpaRepository.findByUsername(jwtTokenUtil.getUsernameFromHeader(httpHeaders));
//        User to = userJpaRepository.findByUsername(messageChat.getTo().getUsername());
//        messageChat.setFrom(from);
//        messageChat.setTo(to);
//        messageChat.setDate(new Date());
//        this.chatMessageJpaRepository.save(messageChat);
//        return new ResponseEntity<>(new ResponseMessage("Messaggio mandato correttamente"), HttpStatus.OK);
//    }
