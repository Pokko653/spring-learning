package com.example.blogging_platform.post;

import com.example.blogging_platform.writer.Writer;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/** Entity class for representing a post */
@Entity
public class Post {

    /** Post ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Post title */
    private String title;

    /** Post contents */
    @Lob
    private String content;

    /** Timestamp representing when the post is created */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /** Author of this post */
    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false)
    @JsonBackReference
    private Writer writer;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
