package com.example.blogging_platform.writer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/writers")
public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping
    public ResponseEntity<List<Writer>> getAllWriters() {
        return ResponseEntity.ok(writerService.getAllWriters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Writer> getWriterById(@PathVariable Long id) {
        return writerService.getWriterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Writer> createWriter(@RequestBody Writer writer) {
        Writer createdWriter = writerService.createWriter(writer);
        return ResponseEntity.ok(createdWriter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Writer> updateWriter(
            @PathVariable Long id,
            @RequestBody Writer updatedWriter) {
        try {
            Writer writer = writerService.updateWriter(id, updatedWriter);
            return ResponseEntity.ok(writer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWriter(@PathVariable Long id) {
        try {
            writerService.deleteWriter(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
