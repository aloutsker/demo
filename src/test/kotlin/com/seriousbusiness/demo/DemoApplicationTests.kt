package com.seriousbusiness.demo

import com.seriousbusiness.demo.testentity.SortOrder
import com.seriousbusiness.demo.testentity.TestEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoApplicationTests {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private final val url = "http://localhost:8080/api/test"

    @Test
    fun `test create`() {
        val te = testEntity()
        val json = mapper.writeValueAsString(te)
        val response = restTemplate.postForEntity(url, httpRequest(json), String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `test find`() {
        val te = testEntity()
        val response1 = restTemplate.getForEntity("http://localhost:8080/api/test/${te.id}", String::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response1.statusCode)

        val json = mapper.writeValueAsString(te)
        restTemplate.postForEntity(url, httpRequest(json), String::class.java)

        val response2 = restTemplate.getForEntity("http://localhost:8080/api/test/${te.id}", String::class.java)
        assertEquals(HttpStatus.OK, response2.statusCode)
        assertEquals(json, response2.body)
    }

    @Test
    fun `test update`() {
        val te = testEntity()
        val json = mapper.writeValueAsString(te)
        restTemplate.postForEntity(url, httpRequest(json), String::class.java)

        val te2 = testEntity()
        te2.id = te.id
        val json2 = mapper.writeValueAsString(te2)
        restTemplate.patchForObject("$url/${te.id}", httpRequest(json2), String::class.java)

        val responseAfterPatch = restTemplate.getForEntity("http://localhost:8080/api/test/${te.id}", String::class.java)
        val patchedTe = mapper.readValue(responseAfterPatch.body, TestEntity::class.java)
        assertEquals(te2, patchedTe)
    }

    @Test
    fun `test delete`() {
        val te = testEntity()
        val json = mapper.writeValueAsString(te)
        restTemplate.postForEntity(url, httpRequest(json), String::class.java)

        restTemplate.delete("$url/${te.id}")

        val response = restTemplate.getForEntity("http://localhost:8080/api/test/${te.id}", String::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }


    private fun httpRequest(body: String): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity(body, headers)
    }

    private fun testEntity() = TestEntity(
        id = UUID.randomUUID(),
        documentId = UUID.randomUUID(),
        documentDate = Instant.now().truncatedTo(ChronoUnit.SECONDS),
        dictionaryValueId = UUID.randomUUID(),
        dictionaryValueName = "aaaa",
        sortOrder = SortOrder.ASC,
        testId = UUID.randomUUID(),
        testName = "zzzz",
    )
}
