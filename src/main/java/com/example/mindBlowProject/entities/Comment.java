package com.example.mindBlowProject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Document(collection = "comments")
@TypeAlias("comment")
public class Comment {

    @Id
    private String id;
    private String text;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private User user;

    @DBRef
    @JsonManagedReference
    private List<User> users;

    public Comment(){}

    public Comment(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
    }
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
