package com.example.blogging_platform.writer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller bean for writer */
@RestController
@RequestMapping("/api/writers")
public class WriterController {

    /** Service bean for writer */
    private final WriterService writerService;

    /** Constructor */
    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    /**
     * Show all writers in database
     * 
     * @return All writers with HTTP 200(OK)
     */
    @GetMapping
    public ResponseEntity<List<Writer>> getAllWriters() {
        return ResponseEntity.ok(writerService.getAllWriters());
    }
    
    /**
     * Show one writer with single ID
     * 
     * @param id Writer ID to show
     * @return Fetched writer with HTTP 200(OK). If no such writer, return HTTP 404(Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Writer> getWriterById(@PathVariable Long id) {
        return writerService.getWriterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new writer
     * 
     * @param writer New writer
     * @return Created writer with HTTP 200(OK)
     */
    @PostMapping
    public ResponseEntity<Writer> createWriter(@RequestBody Writer writer) {
        Writer createdWriter = writerService.createWriter(writer);
        return ResponseEntity.ok(createdWriter);
    }

    /**
     * Update writer's information with single ID
     * 
     * @param id Writer ID to update
     * @param updatedWriter New information with which old writer is replaced
     * @return Updated writer with HTTP 200(OK). If no such writer, return HTTP 404(Not Found)
     */
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

    /**
     * Delete a writer with single ID
     * 
     * @param id Writer ID to delete
     * @return HTTP 204(No Content). If no such writer, return HTTP 404(Not Found)
     */
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
