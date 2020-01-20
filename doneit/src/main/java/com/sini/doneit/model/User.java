package com.sini.doneit.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String name;
    private String surname;
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Todo> todoList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Proposal> proposals;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PersonalCard personalCard;


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
        todo.setUser(this);
    }

    public void removeTodo(Todo todo) {
        todoList.remove(todo);
        todo.setUser(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalCard getPersonalCard() {
        return personalCard;
    }

    public void setPersonalCard(PersonalCard personalCard) {
        this.personalCard = personalCard;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public boolean isOwnerOfTodo(Todo t){
        return todoList.contains(t);
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}