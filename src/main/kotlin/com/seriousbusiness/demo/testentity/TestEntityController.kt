package com.seriousbusiness.demo.testentity

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.transaction.Transactional

@Transactional
@RestController
@RequestMapping("/api")
class TestEntityController(
    val repository: TestEntityRepository
) {

    @PostMapping("/test", consumes = [APPLICATION_JSON_VALUE])
    fun create(@RequestBody te: TestEntity) {
        repository.save(te)
    }

    @GetMapping("/test/{id}", produces = [APPLICATION_JSON_VALUE])
    fun find(@PathVariable(name = "id") id: String ): TestEntity {
        return repository.findById(UUID.fromString(id)).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Found! id=$id")
        }
    }

    @PatchMapping("/test/{id}", consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun update(
        @PathVariable(name = "id") id: UUID,
        @RequestBody updated: TestEntity
    ) {
        val entity = repository.findById(id).orElse(updated)
        entity.id = id
        entity.documentId = updated.documentId
        entity.documentDate = updated.documentDate
        entity.dictionaryValueId = updated.dictionaryValueId
        entity.dictionaryValueName = updated.dictionaryValueName
        entity.sortOrder = updated.sortOrder
        entity.testId = updated.testId
        entity.testName = updated.testName
        repository.save(entity)
    }

    @DeleteMapping("/test/{id}")
    fun delete(@PathVariable(name = "id") id: UUID) {
        repository.deleteById(id)
    }
}