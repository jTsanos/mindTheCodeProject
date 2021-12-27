package com.example.mindBlowProject.entities.api;

import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository repository;

     UserController(UserRepository repository) {
        this.repository = repository;
    }

   @GetMapping(value = "/api/users")
    List<User> GetUsers(){
       return repository.findAll();
   }
           // @RequestParam(name = "userStartsWith", required = false) String userStartsWith
   // ) {

        //if (userStartsWith != null && userStartsWith != "") {
         //  return repository.findUserStartingWith(userStartsWith);
       // }
     //   return repository.findAll();


    @GetMapping("/api/users/{id}")
    User GetUser(@PathVariable("id") String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find user with id " + id));
    }

    @PutMapping("/api/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable String id) {

        return repository.findById(id)
                .map(match -> {
                    match.setFirstName(newUser.getFirstName());
                    match.setLastName(newUser.getLastName());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/api/users")
    void deleteAllCars() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/users/{id}")
    void deleteCar(@PathVariable String id) {
        repository.deleteById(id);
    }
}
