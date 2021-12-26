package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    public List<Address> findAddressStartingWith(String startsWith);
}