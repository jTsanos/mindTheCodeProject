package com.example.mindBlowProject.entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.mail.Address;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.stream.events.Comment;
import java.util.List;

@Entity
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    @DBRef

    private List<Address> addressList;

    @DBRef
    private List<Comment> commentList;

    public User(){}

    public User(String firstName, String lastName, List<Address> addressList, List<Comment> commentList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressList = addressList;
        this.commentList = commentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}