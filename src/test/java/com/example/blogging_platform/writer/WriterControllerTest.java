package com.example.blogging_platform.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Test for controller bean for writer */
@WebMvcTest(WriterController.class)
class WriterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WriterService writerService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test creating a writer with valid writer
     * 
     * @throws Exception
     */
    @Test
    void testCreateWriter_validWriter() throws Exception {
        Writer writer = new Writer();
        writer.setName("John Doe");
        writer.setEmail("john.doe@example.com");

        when(writerService.createWriter(any(Writer.class))).thenReturn(writer);

        mockMvc.perform(post("/api/writers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(writer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(writerService).createWriter(any(Writer.class));
    }

    /**
     * Test showing all writers
     * 
     * @throws Exception
     */
    @Test
    void testGetAllWriters() throws Exception {
        Writer writer1 = new Writer();
        writer1.setId(1L);
        writer1.setName("John Doe");
        writer1.setEmail("john.doe@example.com");

        Writer writer2 = new Writer();
        writer2.setId(2L);
        writer2.setName("Jane Doe");
        writer2.setEmail("jane.doe@example.com");

        List<Writer> writers = Arrays.asList(writer1, writer2);

        when(writerService.getAllWriters()).thenReturn(writers);

        mockMvc.perform(get("/api/writers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));

        verify(writerService).getAllWriters();
    }

    /**
     * Test showing a writer with existing ID
     * 
     * @throws Exception
     */
    @Test
    void testGetWriterById_existingId() throws Exception {
        Writer writer = new Writer();
        writer.setId(1L);
        writer.setName("John Doe");

        when(writerService.getWriterById(1L)).thenReturn(Optional.of(writer));

        mockMvc.perform(get("/api/writers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(writerService).getWriterById(1L);
    }

    /**
     * Test updating writer with existing ID and valid contents
     * 
     * @throws Exception
     */
    @Test
    void testUpdateWriter_existingId_validContent() throws Exception {
        Writer writer = new Writer();
        writer.setName("Updated Name");
        writer.setEmail("updated.email@example.com");

        when(writerService.updateWriter(eq(1L), any(Writer.class))).thenReturn(writer);

        mockMvc.perform(put("/api/writers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(writer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated.email@example.com"));

        verify(writerService).updateWriter(eq(1L), any(Writer.class));
    }

    /**
     * Test deleting writer with existing ID
     * 
     * @throws Exception
     */
    @Test
    void testDeleteWriter_existingId() throws Exception {
        doNothing().when(writerService).deleteWriter(1L);

        mockMvc.perform(delete("/api/writers/1"))
                .andExpect(status().isNoContent());

        verify(writerService).deleteWriter(1L);
    }
}
