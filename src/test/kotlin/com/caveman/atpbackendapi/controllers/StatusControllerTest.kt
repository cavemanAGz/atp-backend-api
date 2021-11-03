package com.caveman.atpbackendapi.controllers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@WebMvcTest
internal class StatusControllerTest {

    @Autowired
    private lateinit var statusController: StatusController

    @Test
    fun `Should respond with message if running`() {
        assert(true)

        val expected = "I AM ALIIIIIVVVVVVEEEEEE!"

        val result = statusController.health()

        assertThat(result).isEqualTo(expected)

    }
}