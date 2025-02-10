package com.example.blogging_platform.writer

import com.example.blogging_platform.post.Post
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class Writer {
    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String? = null

    @Column(unique = true, nullable = false)
    var email: String? = null

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    @OneToMany(mappedBy = "writer", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var posts: List<Post> = ArrayList()

    // Custom setter
    fun editWriterInfo(updatedWriter: Writer) {
        this.name = updatedWriter.name
        this.email = updatedWriter.email
    }
}
