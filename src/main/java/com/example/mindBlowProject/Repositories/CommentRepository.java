package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.Comment;
import com.example.mindBlowProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<User> findAddressStartingWith(String startsWith);
}