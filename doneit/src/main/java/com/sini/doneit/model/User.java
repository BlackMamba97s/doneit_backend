package com.sini.doneit.model;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList;


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
}