package com.example.blogging_platform.post;

import com.example.blogging_platform.writer.Writer;
import com.example.blogging_platform.writer.WriterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** Test for service bean for post */
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private WriterRepository writerRepository;

    @InjectMocks
    private PostService postService;

    /** Field for sample post to be tested */
    private Post post;

    /** Set up a sample post before testing */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create sample post
        post = new Post();
        post.setId(1L);
        post.setTitle("Sample Title");
        post.setContent("Sample Content");
    }

    /** Test for creating a new post with valid post */
    @Test
    void testCreatePost() {
        // Create a sample PostDto
        PostDto postDto = new PostDto();
        postDto.setTitle("Sample Title");
        postDto.setContent("Sample Content");
        postDto.setWriterId(1L);

        // Create a sample Writer to mock its retrieval
        Writer writer = new Writer();
        writer.setId(1L);
        writer.setName("John Doe");
        writer.setEmail("john.doe@example.com");

        // Mock repository behavior
        when(writerRepository.findById(1L)).thenReturn(Optional.of(writer));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Post createdPost = postService.createPost(postDto);

        // Verify and assert
        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getTitle()).isEqualTo("Sample Title");
        assertThat(createdPost.getContent()).isEqualTo("Sample Content");
        assertThat(createdPost.getWriter()).isNotNull();
        assertThat(createdPost.getWriter().getName()).isEqualTo("John Doe");

        // Verify interactions with the repositories
        verify(writerRepository).findById(1L);
        verify(postRepository).save(any(Post.class));
    }

    /** Test fetching a post with existing ID */
    @Test
    void testFindPostById() {
        // Mock repository behavior
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // Call the service method
        Post foundPost = postService.getPostById(1L);

        // Verify and assert
        assertThat(foundPost.getId()).isEqualTo(1L);
        assertThat(foundPost.getTitle()).isEqualTo("Sample Title");

        // Verify interactions with the repository
        verify(postRepository).findById(1L);
    }

    /** Test deleting a post with existing ID */
    @Test
    void testDeletePost() {
        // Mock repository behavior to simulate post existence
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.existsById(postId)).thenReturn(true);

        // Call the service method
        postService.deletePost(postId);

        // Verify interactions with the repository
        verify(postRepository).deleteById(postId);
    }

}
