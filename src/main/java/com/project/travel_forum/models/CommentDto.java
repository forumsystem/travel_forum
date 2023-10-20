package com.project.travel_forum.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentDto {
    @NotNull(message = "Content can't be empty")
    @Size(min = 10, max = 4000, message = "Comment should be between 10 and 4000 symbols")
    private String comment;

    public CommentDto() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
