package com.caveman.atpbackendapi.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@ComponentScan(basePackages = [
    "com.caveman.atpbackendapi.services"
])
@WebMvcTest
internal class StatusControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    // TODO: Figure out what style I like better. This goes with the TODO below
//    @Autowired
//    private lateinit var statusController: StatusController

    @Test
    fun `Should respond with message if running NO_AUTH`() {

        // TODO: This is a different way of accessing the context
//        val expected = "I AM ALIIIIIVVVVVVEEEEEE!"
//        val result = statusController.health()
//        assertThat(result).isEqualTo(expected)

        val output = mockMvc
            .perform(get("/status/health/"))
            .andExpect(
                status().isOk
            )
            .andReturn()

        assertEquals("I AM ALIIIIIVVVVVVEEEEEE!", output.response.contentAsString)
    }

    @Test
    fun `Security should not allow this`() {
        mockMvc.perform(get("/status/")).andExpect(status().is4xxClientError)
    }

    @Test
    fun `Hit protected End Point with Valid Creds`() {

    }

}
