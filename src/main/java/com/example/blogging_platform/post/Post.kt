package com.example.blogging_platform.post

import com.example.blogging_platform.writer.Writer
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class Post {
    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null

    @Lob
    var content: String? = null

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false)
    @JsonBackReference
    var writer: Writer? = null
}
