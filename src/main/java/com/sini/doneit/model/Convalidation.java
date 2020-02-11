package com.sini.doneit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Convalidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String proponent; //utente che ha portato a termine il todo
    private String key;
    private Long todo; // todo convalidato
    private Date date = new Date();

    public Convalidation() {
    }

    public Convalidation(String proponent, String key, Long todo){
        this.proponent = proponent;
        this.key = key;
        this.todo = todo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProponent() {
        return proponent;
    }

    public void setProponent(String proponent) {
        this.proponent = proponent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTodo() {
        return todo;
    }

    public void setTodo(Long todo) {
        this.todo = todo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Convalidation{" +
                "id=" + id +
                ", proponent=" + proponent +
                ", key='" + key + '\'' +
                ", todo=" + todo +
                '}';
    }
}
