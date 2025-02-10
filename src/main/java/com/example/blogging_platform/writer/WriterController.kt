package com.example.blogging_platform.writer

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/writers")
class WriterController(private val writerService: WriterService) {
    @get:GetMapping
    val allWriters: ResponseEntity<List<Writer?>>
        get() = ResponseEntity.ok(writerService.allWriters)

    @GetMapping("/{id}")
    fun getWriterById(@PathVariable id: Long): ResponseEntity<Writer> {
        return writerService.getWriterById(id)
            .map<ResponseEntity<Writer>> { body: Writer? ->
                ResponseEntity.ok(
                    body
                )
            }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createWriter(@RequestBody writer: Writer): ResponseEntity<Writer> {
        val createdWriter = writerService.createWriter(writer)
        return ResponseEntity.ok(createdWriter)
    }

    @PutMapping("/{id}")
    fun updateWriter(
        @PathVariable id: Long,
        @RequestBody updatedWriter: Writer
    ): ResponseEntity<Writer> {
        try {
            val writer = writerService.updateWriter(id, updatedWriter)
            return ResponseEntity.ok(writer)
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteWriter(@PathVariable id: Long): ResponseEntity<Void> {
        try {
            writerService.deleteWriter(id)
            return ResponseEntity.noContent().build()
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }
}
