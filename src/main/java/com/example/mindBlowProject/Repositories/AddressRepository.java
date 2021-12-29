package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByStreet(String addressName);


}