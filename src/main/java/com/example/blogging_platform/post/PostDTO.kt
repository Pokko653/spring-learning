package com.example.blogging_platform.post

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class PostDTO {
    // Getters and Setters
    var title: @NotBlank(message = "Title cannot be blank") @Size(
        max = 100,
        message = "Title must be less than 100 characters"
    ) String? = null

    var content: @NotBlank(message = "Content cannot be blank") String? = null

    var writerId: Long? = null
}
