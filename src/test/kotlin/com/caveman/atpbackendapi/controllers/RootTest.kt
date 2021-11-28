package com.caveman.atpbackendapi.controllers

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@ConfigurationPropertiesScan(basePackages = ["com.caveman.atpbackendapi.security"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class RootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun `Valid credentials get to protected endpoint`() {

        mockMvc.perform(get("/")
            .header("username", "admin")
            .header("password", "password"))
        .andExpect(status().isOk)
    }

    @Test
    fun `In-Valid credentials fail to get to protected endpoint`() {

        mockMvc.perform(get("/")
            .header("username", "admin")
            .header("password", "passw"))
            .andExpect(status().isUnauthorized)
    }
}