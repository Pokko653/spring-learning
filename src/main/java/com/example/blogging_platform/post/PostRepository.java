package com.example.blogging_platform.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for post */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // ...
}
