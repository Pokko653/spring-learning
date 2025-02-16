package com.example.blogging_platform.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Data transfer object for post */
public class PostDto {
    /** Post title */
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    /** Post content */
    @NotBlank(message = "Content cannot be blank")
    private String content;

    /** Author's ID */
    private Long writerId;

    // Getters and Setters
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

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }
}
