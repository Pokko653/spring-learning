package com.example.blogging_platform.writer

import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class WriterService(private val writerRepository: WriterRepository) {
    val allWriters: List<Writer?>
        get() = writerRepository.findAll()

    fun getWriterById(id: Long): Optional<Writer?> {
        return writerRepository.findById(id)
    }

    fun createWriter(writer: Writer): Writer {
        return writerRepository.save(writer)
    }

    fun updateWriter(id: Long, updatedWriter: Writer): Writer {
        return writerRepository.findById(id)
            .map<Writer>(Function { existingWriter: Writer? ->
                existingWriter?.editWriterInfo(updatedWriter)
                existingWriter?.let { writerRepository.save(it) }
            })
            .orElseThrow {
                RuntimeException(
                    "Writer not found with id $id"
                )
            }
    }

    fun deleteWriter(id: Long) {
        if (!writerRepository.existsById(id)) {
            throw RuntimeException("Writer not found with id $id")
        }
        writerRepository.deleteById(id)
    }
}
