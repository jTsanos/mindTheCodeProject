package com.example.mindBlowProject.mvc.models;

import org.springframework.stereotype.Component;

@Component
public class SearchByAddressName {

    private String addressName;

    public SearchByAddressName(){}
    public SearchByAddressName(String addressName) {
        this.addressName = addressName;
    }
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
