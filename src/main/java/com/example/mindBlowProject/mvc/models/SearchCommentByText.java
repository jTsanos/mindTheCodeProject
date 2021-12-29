package com.example.mindBlowProject.mvc.models;

import org.springframework.stereotype.Component;

@Component
public class SearchCommentByText {
    private String commentText;

    public SearchCommentByText(){}
    public SearchCommentByText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
