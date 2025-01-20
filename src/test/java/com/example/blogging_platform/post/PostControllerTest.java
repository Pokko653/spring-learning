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

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper; // For JSON serialization

    @Test
    void testCreatePost() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Sample Title");
        postDTO.setContent("Sample Content");
        postDTO.setWriterId(1L);

        Post createdPost = new Post();
        createdPost.setId(1L);
        createdPost.setTitle(postDTO.getTitle());
        createdPost.setContent(postDTO.getContent());

        when(postService.createPost(any(PostDTO.class))).thenReturn(createdPost);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.content").value("Sample Content"));

        verify(postService).createPost(any(PostDTO.class));
    }

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

    @Test
    void testUpdatePost() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Updated Title");
        postDTO.setContent("Updated Content");
        postDTO.setWriterId(1L);

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle(postDTO.getTitle());
        updatedPost.setContent(postDTO.getContent());

        when(postService.updatePost(eq(1L), any(PostDTO.class))).thenReturn(updatedPost);

        mockMvc.perform(put("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));

        verify(postService).updatePost(eq(1L), any(PostDTO.class));
    }

    @Test
    void testDeletePost() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService).deletePost(1L);
    }
}
