package com.example.mindBlowProject.Repositories;

import com.example.mindBlowProject.entities.Comment;
import com.example.mindBlowProject.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findCommentByText(String commentText);


}