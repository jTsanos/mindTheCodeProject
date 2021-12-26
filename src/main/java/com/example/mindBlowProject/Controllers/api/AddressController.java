package com.example.mindBlowProject.Controllers.api;

import com.example.mindBlowProject.Repositories.AddressRepository;
import com.example.mindBlowProject.entities.Address;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AddressController {

    private final AddressRepository repository;

    public AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/comments")
    List<Address> GetAddresses() {
        return repository.findAll();
    }

    @GetMapping("/api/comments/{id}")
    Address GetAddress(@PathVariable("id") String id) {
        return repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Cannot find address with id " + id));
    }

    @PutMapping("/api/comments/{id}")
    Address updateAddress(@RequestBody Address newAddress, @PathVariable String id) {

        return repository.findById(Long.valueOf(id))
                .map(match -> {
                    match.setUsers(newAddress.getUsers());
                    match.setPostalCode(newAddress.getPostalCode());
                    match.setStreet(newAddress.getStreet());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newAddress.setId(id);
                    return repository.save(newAddress);
                });
    }

    @DeleteMapping("/api/comments")
    void deleteAllCars() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/comments/{id}")
    void deleteCar(@PathVariable String id) {
        repository.deleteById(Long.valueOf(id));
    }
}
