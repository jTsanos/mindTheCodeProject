package com.example.mindBlowProject.entities.api;

import com.example.mindBlowProject.Repositories.AddressRepository;
import com.example.mindBlowProject.entities.Address;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AddressController {

    private final AddressRepository repository;

     AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/addresses")
    List<Address> GetAddresses() {
        return repository.findAll();
    }

    @GetMapping("/api/addresses/{id}")
    Address GetAddress(@PathVariable("id") String id) {
        return repository.findById("id")
                .orElseThrow(() -> new RuntimeException("Cannot find address with id " + id));
    }

    @PutMapping("/api/addresses/{id}")
    Address updateAddress(@RequestBody Address newAddress, @PathVariable String id) {

        return repository.findById("id")
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

    @DeleteMapping("/api/addresses")
    void deleteAllCars() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/addresses/{id}")
    void deleteCar(@PathVariable String id) {
        repository.deleteById("id");
    }
}
