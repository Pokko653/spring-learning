package com.example.blogging_platform.writer;

import com.example.blogging_platform.post.Post;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Entity class for representing a writer */
@Entity
public class Writer {

    /** Writer ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Writer name */
    private String name;

    /** Writer's e-mail address */
    @Column(unique = true, nullable = false)
    private String email;

    /** Timestamp representing when the writer signed up */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /** Posts by this writer */
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Custom setter
    public void editWriterInfo(Writer updatedWriter) {
        this.name = updatedWriter.getName();
        this.email = updatedWriter.getEmail();
    }
}
