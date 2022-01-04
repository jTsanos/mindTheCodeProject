package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByStreet(String addressName);

    List<Address> findAddressByUsers(List<User> users);


}