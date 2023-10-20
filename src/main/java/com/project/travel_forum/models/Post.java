package com.project.travel_forum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    //TODO -- comment

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "likes",
                joinColumns = @JoinColumn(name = "post_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes;
    //boolean isLiked and delete like_id

    public Post() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Post post = (Post) obj;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
