package com.example.blogging_platform.writer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for writer */
@Repository
public interface WriterRepository extends JpaRepository<Writer, Long> {
    // ...
}
