package com.example.blogging_platform.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Test for controller bean for post */
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper; // For JSON serialization

    /**
     * Test creating post when valid post is given
     * 
     * @throws Exception
     */
    @Test
    void testCreatePost() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setTitle("Sample Title");
        postDto.setContent("Sample Content");
        postDto.setWriterId(1L);

        Post createdPost = new Post();
        createdPost.setId(1L);
        createdPost.setTitle(postDto.getTitle());
        createdPost.setContent(postDto.getContent());

        when(postService.createPost(any(PostDto.class))).thenReturn(createdPost);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.content").value("Sample Content"));

        verify(postService).createPost(any(PostDto.class));
    }

    /**
     * Test showing all posts
     * 
     * @throws Exception
     */
    @Test
    void testGetAllPosts() throws Exception {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Title 1");
        post1.setContent("Content 1");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Title 2");
        post2.setContent("Content 2");

        List<Post> posts = Arrays.asList(post1, post2);

        when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[1].title").value("Title 2"));

        verify(postService).getAllPosts();
    }

    /**
     * Test showing a post with existing ID
     * 
     * @throws Exception
     */
    @Test
    void testGetPostById() throws Exception {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Title 1");
        post.setContent("Content 1");

        when(postService.getPostById(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title 1"))
                .andExpect(jsonPath("$.content").value("Content 1"));

        verify(postService).getPostById(1L);
    }

    /**
     * Test updating post with existing ID and valid contents
     * 
     * @throws Exception
     */
    @Test
    void testUpdatePost() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Title");
        postDto.setContent("Updated Content");
        postDto.setWriterId(1L);

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle(postDto.getTitle());
        updatedPost.setContent(postDto.getContent());

        when(postService.updatePost(eq(1L), any(PostDto.class))).thenReturn(updatedPost);

        mockMvc.perform(put("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));

        verify(postService).updatePost(eq(1L), any(PostDto.class));
    }

    /**
     * Test deleting post with existing ID
     * 
     * @throws Exception
     */
    @Test
    void testDeletePost() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService).deletePost(1L);
    }
}
