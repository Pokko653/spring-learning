package com.example.blogging_platform.writer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WriterService {

    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public List<Writer> getAllWriters() {
        return writerRepository.findAll();
    }

    public Optional<Writer> getWriterById(Long id) {
        return writerRepository.findById(id);
    }

    public Writer createWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    public Writer updateWriter(Long id, Writer updatedWriter) {
        return writerRepository.findById(id)
                .map(existingWriter -> {
                    existingWriter.editWriterInfo(updatedWriter);
                    return writerRepository.save(existingWriter);
                })
                .orElseThrow(() -> new RuntimeException("Writer not found with id " + id));
    }

    public void deleteWriter(Long id) {
        if (!writerRepository.existsById(id)) {
            throw new RuntimeException("Writer not found with id " + id);
        }
        writerRepository.deleteById(id);
    }
}
