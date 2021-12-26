package com.example.mindBlowProject.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Document(collection = "addresses")
public class Address {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String street;
    private String postalCode;

    public Address(){

    }

    public Address(String street, String postalCode) {
        this.street = street;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
