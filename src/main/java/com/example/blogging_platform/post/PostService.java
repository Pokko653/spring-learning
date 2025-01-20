package com.example.blogging_platform.post;

import com.example.blogging_platform.writer.Writer;
import com.example.blogging_platform.writer.WriterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final WriterRepository writerRepository;

    public PostService(PostRepository postRepository, WriterRepository writerRepository) {
        this.postRepository = postRepository;
        this.writerRepository = writerRepository;
    }

    // Create a new post
    public Post createPost(PostDTO postDTO) {
        Writer writer = writerRepository.findById(postDTO.getWriterId())
                .orElseThrow(() -> new RuntimeException("Writer not found with id " + postDTO.getWriterId()));
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setWriter(writer);
        return postRepository.save(post);
    }

    // Retrieve all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Retrieve a single post by ID
    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post with ID " + id + " not found");
        }
        return post.get();
    }

    // Update a post by ID
    public Post updatePost(Long id, PostDTO postDTO) {
        Post post = getPostById(id); // Reuse existing method to fetch post or throw error
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return postRepository.save(post);
    }

    // Delete a post by ID
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException("Post with ID " + id + " not found");
        }
        postRepository.deleteById(id);
    }
}
