package com.skillbox.fibonacci;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureMockMvc
class FibonacciControllerTest extends PostgresTestContainerInitializer {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Getting the fibonacci number by HTTP request")
    public void testGetFibonacciNumberByHTTPRequest() throws Exception {
        mockMvc.perform(get("/fibonacci/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.index").value(1))
                .andExpect(jsonPath("$.value").value(1));
    }

    @Test
    @DisplayName("Getting the Fibonacci number from an HTTP request with the number 0")
    public void testGetFibonacciNumberByHTTPRequestWithNumberOne() throws Exception {
        mockMvc.perform(get("/fibonacci/0"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Index should be greater or equal to 1"));

    }

}
