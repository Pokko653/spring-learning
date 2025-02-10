package com.example.blogging_platform.writer;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Test for service bean for writer */
class WriterServiceTest {

    @Mock
    private WriterRepository writerRepository;

    @InjectMocks
    private WriterService writerService;

    public WriterServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /** Test creating a new writer with valid writer */
    @Test
    void testCreateWriter_validWriter() {
        Writer writer = new Writer();
        writer.setName("John Doe");
        writer.setEmail("john.doe@example.com");

        when(writerRepository.save(writer)).thenReturn(writer);

        Writer createdWriter = writerService.createWriter(writer);

        assertNotNull(createdWriter);
        assertEquals("John Doe", createdWriter.getName());
        verify(writerRepository).save(writer);
    }

    /** Test fetching a writer with existing ID */
    @Test
    void testGetWriterById_existingId() {
        Writer writer = new Writer();
        writer.setId(1L);
        writer.setName("John Doe");

        when(writerRepository.findById(1L)).thenReturn(Optional.of(writer));

        Optional<Writer> foundWriter = writerService.getWriterById(1L);

        assertNotNull(foundWriter);
        assertTrue(foundWriter.isPresent());
        assertEquals("John Doe", foundWriter.get().getName());
        verify(writerRepository).findById(1L);
    }

    /** Test updating a writer with existing Id and valid contents */
    @Test
    void testUpdateWriter_existingId_validContent() {
        Writer existingWriter = new Writer();
        existingWriter.setId(1L);
        existingWriter.setName("John Doe");
        existingWriter.setEmail("old.email@example.com");

        Writer updatedWriter = new Writer();
        updatedWriter.setName("Jane Doe");
        updatedWriter.setEmail("new.email@example.com");

        when(writerRepository.findById(1L)).thenReturn(Optional.of(existingWriter));
        when(writerRepository.save(existingWriter)).thenReturn(existingWriter);

        Writer result = writerService.updateWriter(1L, updatedWriter);

        assertEquals("Jane Doe", result.getName());
        assertEquals("new.email@example.com", result.getEmail());
        verify(writerRepository).save(existingWriter);
    }

    /** Test deleting writer with existing ID */
    @Test
    void testDeleteWriter_existingId() {
        // Mock repository behavior to simulate post existence
        Writer writer = new Writer();
        Long writerId = 1L;
        writer.setId(1L);
        writer.setName("John Doe");
        writer.setEmail("email@example.com");
        when(writerRepository.findById(writerId)).thenReturn(Optional.of(writer));
        when(writerRepository.existsById(writerId)).thenReturn(true);

        // Call the service method
        writerService.deleteWriter(writerId);

        // Verify interactions with the repository
        verify(writerRepository).deleteById(writerId);
    }
}
