package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.Comment;
import com.example.mindBlowProject.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {


}