package com.seriousbusiness.demo

import com.zaxxer.hikari.HikariDataSource
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers


@Configuration
@Testcontainers
class TestConfig {

    @Bean(initMethod = "start", destroyMethod = "close")
    fun postgresContainer() = PostgreSQLContainer("postgres:14")

    @Bean
    fun dataSource() = HikariDataSource().apply {
        val postgres = postgresContainer()
        jdbcUrl = postgres.jdbcUrl
        username = postgres.username
        password = postgres.password
        driverClassName = postgres.driverClassName
    }

    // RestTemplate не дружит с методом PATCH; такая конфигурация чинит проблему
    // По-хорошему, надо использовать какой-нибудь другой http-клиент, но мне уже лениво переписывать х)
    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        val httpClient: HttpClient = HttpClientBuilder.create().build()
        val requestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
        restTemplate.requestFactory = requestFactory
        return restTemplate
    }
}