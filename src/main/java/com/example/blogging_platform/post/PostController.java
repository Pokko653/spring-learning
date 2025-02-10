package com.example.blogging_platform.post;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller for services related to post */
@RestController
@RequestMapping("/posts")
public class PostController {

    /** Service bean for post */
    private final PostService postService;

    /** Constructor */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Create a new post
     * 
     * @param postDto Post title and contents
     * @return Created post with HTTP 200(OK)
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDto postDto) {
        Post createdPost = postService.createPost(postDto);
        return ResponseEntity.ok(createdPost);
    }

    /**
     * Show all posts uploaded in the database
     * 
     * @return All posts with HTTP 200(OK)
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * Show one post that have specific ID
     * 
     * @param id PostID to fetch 
     * @return Fetched post with HTTP 200(OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    /**
     * Update(Edit) post, including titles and contents
     * 
     * @param id Post ID to update
     * @param postDto New contents with which old post is replaced
     * @return Updated post with HTTP 200(OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostDto postDto) {
        Post updatedPost = postService.updatePost(id, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * Delete post with ID
     * 
     * @param id Post Id to delete
     * @return HTTP 204(No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
