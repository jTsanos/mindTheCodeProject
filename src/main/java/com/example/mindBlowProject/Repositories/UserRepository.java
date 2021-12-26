package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    public List<User> findUserStartingWith(String startsWith);
}