package com.example.mindBlowProject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;




import java.util.List;


@Document(collection = "users")
@TypeAlias("user")
public  class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    @DBRef
    @JsonManagedReference
    private List<Address> addressList;

    @DBRef
    @JsonManagedReference
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