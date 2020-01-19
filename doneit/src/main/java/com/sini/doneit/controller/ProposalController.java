package com.sini.doneit.controller;

import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.Proposal;
import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.Todo;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.ProposalJpaRepository;
import com.sini.doneit.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sini.doneit.model.MessageCode.TODO_CREATED;

@RestController
@CrossOrigin("*")
public class ProposalController {

    @Autowired
    ProposalJpaRepository proposalJpaRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @GetMapping(path = "/all-proposal")
    public List<Proposal> getAllProposals() {
        return proposalJpaRepository.findAll();
    }


    @PostMapping(path = "create-proposal")
    public ResponseEntity<ResponseMessage> createProposal(@RequestBody Todo todo, @RequestHeader HttpHeaders headers) {
        String username = jwtTokenUtil.getUsernameFromHeader(headers);
        User user = userJpaRepository.findByUsername(username);

        Proposal proposal = new Proposal(user, todo);

        proposalJpaRepository.save(proposal);

        return new ResponseEntity<>(new ResponseMessage("Todo creato correttamente", TODO_CREATED),
                HttpStatus.OK);
    }


}
