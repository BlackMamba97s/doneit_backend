package com.sini.doneit.controller;

import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.Proposal;
import com.sini.doneit.model.ResponseMessage;
import com.sini.doneit.model.Todo;
import com.sini.doneit.model.User;
import com.sini.doneit.repository.ProposalJpaRepository;
import com.sini.doneit.repository.TodoJpaRepository;
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
    private UserJpaRepository userJpaRepository;

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


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

        return new ResponseEntity<>(new ResponseMessage("Proposta aggiunta correttamente", TODO_CREATED),
                HttpStatus.OK);
    }

    @PostMapping(path = "accept-proposal/{proposalId}")
    public ResponseEntity<ResponseMessage> acceptProposal(@RequestBody Todo todo, @PathVariable Long proposalId) {
        todo.setState("accepted");
        todoJpaRepository.save(todo);
        Proposal proposal = proposalJpaRepository.findById(proposalId).get();
        proposal.setState("accepted");
        proposalJpaRepository.save(proposal);
        todo.getProposals().remove(proposal);
        for (Proposal propos : todo.getProposals()) {
            if (!propos.getState().equals("refused")) {
                Proposal newProp = proposalJpaRepository.findById(propos.getId()).get();
                newProp.setState("refused");
                proposalJpaRepository.save(newProp);
            }
        }

        return new ResponseEntity<>(new ResponseMessage("proposta accettata", TODO_CREATED),
                HttpStatus.OK);
    }

    @PutMapping(path = "refuse-proposal/{proposalId}")
    public ResponseEntity<ResponseMessage> refuseProposal(@PathVariable Long proposalId) {
        Proposal proposal = proposalJpaRepository.findById(proposalId).get();
        proposal.setState("refused");
        proposalJpaRepository.save(proposal);

        return new ResponseEntity<>(new ResponseMessage("proposta rifiutata", TODO_CREATED),
                HttpStatus.OK);
    }


}
