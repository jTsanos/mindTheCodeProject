package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByFirstName(String firstName);
     //List<User> findAllsearchByName(String searchByName);

    Optional<User> findByEmail(String email);

}