package com.caveman.atpbackendapi.controllers

import com.caveman.atpbackendapi.services.HealthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/status")
class StatusController(
    private val healthService: HealthService
) {

    @GetMapping("/health")
    fun health(): String {
        return healthService.greeting()
    }

}
