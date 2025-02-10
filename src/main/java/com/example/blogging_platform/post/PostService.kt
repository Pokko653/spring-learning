package com.example.blogging_platform.post

import com.example.blogging_platform.writer.WriterRepository
import org.springframework.stereotype.Service

@Service
class PostService(private val postRepository: PostRepository, private val writerRepository: WriterRepository) {
    // Create a new post
    fun createPost(postDTO: PostDTO): Post {
        val writer = writerRepository.findById(postDTO.writerId)
            .orElseThrow { RuntimeException("Writer not found with id " + postDTO.writerId) }
        val post = Post()
        post.title = postDTO.title
        post.content = postDTO.content
        post.writer = writer
        return postRepository.save(post)
    }

    val allPosts: MutableList<Post?>
        // Retrieve all posts
        get() = postRepository.findAll()

    // Retrieve a single post by ID
    fun getPostById(id: Long): Post {
        val post = postRepository.findById(id)
        if (post.isEmpty) {
            throw PostNotFoundException("Post with ID $id not found")
        }
        return post.get()
    }

    // Update a post by ID
    fun updatePost(id: Long, postDTO: PostDTO): Post {
        val post = getPostById(id) // Reuse existing method to fetch post or throw error
        post.title = postDTO.title
        post.content = postDTO.content
        return postRepository.save(post)
    }

    // Delete a post by ID
    fun deletePost(id: Long) {
        if (!postRepository.existsById(id)) {
            throw PostNotFoundException("Post with ID $id not found")
        }
        postRepository.deleteById(id)
    }
}
