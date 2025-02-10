package com.example.blogging_platform.post

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService) {
    // Create a new post
    @PostMapping
    fun createPost(@RequestBody postDTO: @Valid PostDTO): ResponseEntity<Post> {
        val createdPost = postService.createPost(postDTO)
        return ResponseEntity.ok(createdPost)
    }

    @get:GetMapping
    val allPosts: ResponseEntity<MutableList<Post?>>
        // Retrieve all posts
        get() {
            val posts = postService.allPosts
            return ResponseEntity.ok(posts)
        }

    // Retrieve a single post by ID
    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<Post> {
        val post = postService.getPostById(id)
        return ResponseEntity.ok(post)
    }

    // Update a post by ID
    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody postDTO: @Valid PostDTO
    ): ResponseEntity<Post> {
        val updatedPost = postService.updatePost(id, postDTO)
        return ResponseEntity.ok(updatedPost)
    }

    // Delete a post by ID
    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Void> {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }
}
