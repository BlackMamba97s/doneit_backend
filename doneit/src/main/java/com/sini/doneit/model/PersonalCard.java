package com.sini.doneit.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "personal_card")
public class PersonalCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String university;
    private String faculty;
    private String telephone;
    private String statusDescription;
    private Boolean done = false;
    @Type(type = "text")
    private String base64StringImage;

    @OneToOne
    @JoinColumn(name = "users")
    private User user;


    public PersonalCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public PersonalCard setUser(User user) {
        this.user = user;
        return this;
    }

    public String getBase64StringImage() {
        return base64StringImage;
    }

    public void setBase64StringImage(String base64StringImage) {
        this.base64StringImage = base64StringImage;
    }

    @Override
    public String toString() {
        return "PersonalCard{" +
                "university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", telephone='" + telephone + '\'' +
                ", statusDescription='" + statusDescription + '\'' +
                ", done=" + done +
                ", user=" + user +
                ", base64String=" + base64StringImage + "}";
    }
}
