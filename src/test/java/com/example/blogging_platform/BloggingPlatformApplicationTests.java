package com.example.blogging_platform;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.blogging_platform.post.PostController;

@SpringBootTest
class BloggingPlatformApplicationTests {

    @Autowired
    private PostController postController;

    @Test
    void contextLoads() {
        // Assert that the application context successfully loads the PostController bean
        assertThat(postController).isNotNull();
    }
}
