package com.example.mindBlowProject.entities.api;

import com.example.mindBlowProject.Repositories.CommentRepository;
import com.example.mindBlowProject.entities.Address;
import com.example.mindBlowProject.entities.Comment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentRepository repository;

     CommentController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/comments")
    List<Comment> GetComments() {
        return repository.findAll();
    }

    @GetMapping("/api/comments/{id}")
    Comment GetComments(@PathVariable("id") String id) {
        return repository.findById("id")
                .orElseThrow(() -> new RuntimeException("Cannot find comment with id " + id));
    }

    @PutMapping("/api/comments/{id}")
    Comment updateComment(@RequestBody Comment newComment, @PathVariable String id) {

        return repository.findById("id")
                .map(match -> {
                    match.setDate(newComment.getDate());
                    match.setText(newComment.getText());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newComment.setId(id);
                    return repository.save(newComment);
                });
    }

    @DeleteMapping("/api/comments")
    void deleteAllUsers() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/comments/{id}")
    void deleteUser(@PathVariable String id) {
        repository.deleteById("id");
    }
}
