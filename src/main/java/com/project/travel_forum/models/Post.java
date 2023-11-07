package com.project.travel_forum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
    @Column(name = "time_stamp")
    @CreationTimestamp
    private Timestamp timestamp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

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

    public int getLikes() {
        return likes.size();
    }

    public void setLikes(User user) {
        likes.add(user);
    }

    public void removeLikes(User user) {
        likes.remove(user);
    }

    public Set<User> getUserLikes(){
        return likes;
    }

    public String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date(timestamp.getTime()));
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public long getCommentsSize() {
        return comments.size();
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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
