package com.project.travel_forum.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PostDto {
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 16, max = 64, message = "Title should be between 16 and 64 symbols")
    private String title;
    @NotEmpty(message = "Content can't be empty")
    @Size(min = 32, max = 8192, message = "Content should be between 31 and 8192 symbols")
    private String content;

    public PostDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}