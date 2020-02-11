package com.sini.doneit.controller;

import com.sini.doneit.jwt.JwtTokenUtil;
import com.sini.doneit.model.*;
import com.sini.doneit.repository.ConvalidationJpaRepository;
import com.sini.doneit.repository.ProposalJpaRepository;
import com.sini.doneit.repository.TodoJpaRepository;
import com.sini.doneit.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.sini.doneit.model.MessageCode.*;

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
    private ConvalidationJpaRepository convalidationJpaRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping(path = "/all-proposal")
    public List<Proposal> getAllProposals() {
        return proposalJpaRepository.findAll();
    }


    @PostMapping(path = "create-proposal")
    public ResponseEntity<ResponseMessage> createProposal(@RequestBody Todo todo, @RequestHeader HttpHeaders headers) {
        String username = jwtTokenUtil.getUsernameFromHeader(headers);
        if(username.equals(todo.getUser().getUsername())){
            return new ResponseEntity<>(new ResponseMessage("proposta non accettata, errore con gli utenti", TODO_CREATED),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userJpaRepository.findByUsername(username);


            Proposal proposal = new Proposal(user, todo);
            proposalJpaRepository.save(proposal);

            return new ResponseEntity<>(new ResponseMessage("Proposta aggiunta correttamente", PROPOSAL_CREATED),
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


    @PutMapping(path = "undo-accept-proposal/todo/{todoId}")
    public ResponseEntity<ResponseMessage> undoAcceptProposal(@PathVariable Long todoId){
        Todo todo = todoJpaRepository.findById(todoId).get();
        todo.setState("published");

        for (Proposal propos : todo.getProposals()) {
            Proposal newProp = proposalJpaRepository.findById(propos.getId()).get();
            newProp.setState("in progress");
            proposalJpaRepository.save(newProp);
        }

        todoJpaRepository.save(todo);

        return new ResponseEntity<>(new ResponseMessage("annullata accettazione del todo", TODO_CREATED),
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

    @PutMapping(path = "undo-refuse-proposal/{proposalId}")
    public ResponseEntity<ResponseMessage> undoRefuseProposal(@PathVariable Long proposalId){
        Proposal proposal = proposalJpaRepository.findById(proposalId).get();
        proposal.setState("in progress");
        proposalJpaRepository.save(proposal);

        return new ResponseEntity<>(new ResponseMessage("rifuto annullato correttamente", TODO_CREATED),
                HttpStatus.OK);
    }

    @GetMapping(path = "get-convalidation-key/{todoId}")
    public String getConvalidationKey(@PathVariable Long todoId, @RequestHeader HttpHeaders headers){
        String proponent = this.jwtTokenUtil.getUsernameFromHeader(headers);
        User user = this.userJpaRepository.findByUsername(proponent);
        Proposal p = this.proposalJpaRepository.findByUserAndTodo(user,todoId);
        if(p != null){
            String key = UUID.randomUUID().toString();
            Convalidation convalidation = new Convalidation(proponent,key,todoId);
            this.convalidationJpaRepository.save(convalidation);
            return key;
        }
        return null;
    }

    @PostMapping(path = "convalidate-todo")
    public ResponseEntity<ResponseMessage> convalidateTodo(@RequestBody Convalidation c, @RequestHeader HttpHeaders headers){
        Convalidation convalidation = this.convalidationJpaRepository.findByTodo(c.getTodo());
        Long difference = (new Date().getTime() - convalidation.getDate().getTime())/1000;
        if(difference <= 30) {
            if (convalidation != null && convalidation.getKey().equals(c.getKey())) {
                Todo todo = this.todoJpaRepository.findById(convalidation.getTodo()).get();

                User user = this.userJpaRepository.findByUsername(convalidation.getProponent());
                user.getPersonalCard().getWallet().addCfu(todo.getCategory().getCfuPrice());
                todo.setState("completed");
                Proposal proposal = this.proposalJpaRepository.findByUserAndTodo(user.getId(), todo.getId());
                proposal.setState("completed");
                this.todoJpaRepository.save(todo);
                this.userJpaRepository.save(user);
                this.proposalJpaRepository.save(proposal);

                return new ResponseEntity<>(new ResponseMessage("convalidazione avvenuta con successo", CONVALIDATION_DONE), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Chiave incongruente", BAD_KEY), HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity<>(new ResponseMessage("timer di convalidazione scaduto",CONVALIDATION_TIMER_EXCEDED),HttpStatus.OK);
        }

    }

    // for android application
    @GetMapping(path = "create-proposal-android/{todoId}")
    public ResponseEntity<ResponseMessage> createProposal (@PathVariable Long todoId, @RequestHeader HttpHeaders headers){
        String username = jwtTokenUtil.getUsernameFromHeader(headers);
        Todo todo = this.todoJpaRepository.findById(todoId).get();
        if(username.equals(todo.getUser().getUsername())){
            return new ResponseEntity<>(new ResponseMessage("proposta non accettata, errore con gli utenti", TODO_CREATED),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userJpaRepository.findByUsername(username);


        Proposal proposal = new Proposal(user, todo);
        proposalJpaRepository.save(proposal);

        return new ResponseEntity<>(new ResponseMessage("Proposta aggiunta correttamente", PROPOSAL_CREATED),
                HttpStatus.OK);
    }

}
