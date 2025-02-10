package com.example.blogging_platform.writer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Service bean for writer */
@Service
public class WriterService {

    /** Repository bean for writer */
    private final WriterRepository writerRepository;

    /** Constructor */
    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    /**
     * Fetch all writers
     * 
     * @return All writers
     */
    public List<Writer> getAllWriters() {
        return writerRepository.findAll();
    }

    /**
     * Fetch a writer with single ID
     * 
     * @param id Writer Id to fetch
     * @return Writer with requested ID. If no such writer, the result is empty.
     */
    public Optional<Writer> getWriterById(Long id) {
        return writerRepository.findById(id);
    }

    /**
     * Create a new writer
     * 
     * @param writer New writer
     * @return Newly created writer
     */
    public Writer createWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    /**
     * Update a writer with new information
     * 
     * @param id Writer Id to update
     * @param updatedWriter New information to be updated
     * @return Updated writer
     * @throws java.lang.RuntimeException No writer is found with given ID
     */
    public Writer updateWriter(Long id, Writer updatedWriter) {
        return writerRepository.findById(id)
                .map(existingWriter -> {
                    existingWriter.editWriterInfo(updatedWriter);
                    return writerRepository.save(existingWriter);
                })
                .orElseThrow(() -> new RuntimeException("Writer not found with id " + id));
    }

    /**
     * Delete a writer with single ID
     * 
     * @param id Writer ID to delete
     * @throws java.lang.RuntimeException No writer is found with given ID
     */
    public void deleteWriter(Long id) {
        if (!writerRepository.existsById(id)) {
            throw new RuntimeException("Writer not found with id " + id);
        }
        writerRepository.deleteById(id);
    }
}
