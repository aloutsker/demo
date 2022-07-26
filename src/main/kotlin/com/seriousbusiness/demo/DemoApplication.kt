package com.seriousbusiness.demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableJpaRepositories
@EnableWebMvc
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

var mapper = ObjectMapper().also {
    it.registerModule(JavaTimeModule())
}
