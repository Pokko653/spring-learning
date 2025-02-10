package com.example.blogging_platform.post;

import com.example.blogging_platform.writer.Writer;
import com.example.blogging_platform.writer.WriterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Service beans for post */
@Service
public class PostService {

    /** Repository bean for post */
    private final PostRepository postRepository;
    /** Repository bean for writer */
    private final WriterRepository writerRepository;

    /** Constructor */
    public PostService(PostRepository postRepository, WriterRepository writerRepository) {
        this.postRepository = postRepository;
        this.writerRepository = writerRepository;
    }

    /**
     * Create and save a new post with DTO of post
     * 
     * @param postDto Data transfer object for new post
     * @return Newly created post
     */
    public Post createPost(PostDto postDto) {
        Writer writer = writerRepository.findById(postDto.getWriterId())
                .orElseThrow(() -> new RuntimeException("Writer not found with id " + postDto.getWriterId()));
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setWriter(writer);
        return postRepository.save(post);
    }

    /**
     * Fetch all posts
     * 
     * @return All posts
     */
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * Fetch one post with single ID
     * 
     * @param id Post ID to fetch
     * @return Fetched post
     */
    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post with ID " + id + " not found");
        }
        return post.get();
    }

    /**
     * Update one post with single ID and new content
     * 
     * @param id Post Id to update
     * @param postDto New contents to be overwritten
     * @return Updated post
     */
    public Post updatePost(Long id, PostDto postDto) {
        Post post = getPostById(id); // Reuse existing method to fetch post or throw error
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return postRepository.save(post);
    }

    /**
     * Delete one post with single ID
     * 
     * @param id Post ID to delete
     */
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException("Post with ID " + id + " not found");
        }
        postRepository.deleteById(id);
    }
}
