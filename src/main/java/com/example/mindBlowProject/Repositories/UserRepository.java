package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByFirstName(String firstName);
     //List<User> findAllsearchByName(String searchByName);
}