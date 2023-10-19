package com.project.travel_forum.models;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Optional;

public class FilterOptions {

    private Optional<String> title;
    private Optional<String> content;
    private Optional<String> createdBy;

    public FilterOptions(
            String title,
            String content,
            String createdBy) {
        this.title = Optional.ofNullable(title);
        this.content = Optional.ofNullable(content);
        this.createdBy = Optional.ofNullable(createdBy);
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getContent() {
        return content;
    }

    public Optional<String> getCreatedBy() {
        return createdBy;
    }

}
