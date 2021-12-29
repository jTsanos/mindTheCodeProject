package com.example.mindBlowProject.mvc.models;

import org.springframework.stereotype.Component;

@Component
public class SearchByName {

    private String firstName;

    public SearchByName(){}
    public SearchByName(String firstName) {
            this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
