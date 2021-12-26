package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}