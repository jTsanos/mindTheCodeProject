package com.example.mindBlowProject.entities;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


import org.springframework.data.annotation.Id;
import java.util.Date;


@Document(collection = "comments")
@TypeAlias("comment")
public class Comment {

    @Id
    private String id;

    private String text;
    private Date date;

    private User user;

    public Comment(){}

    public Comment(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
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
