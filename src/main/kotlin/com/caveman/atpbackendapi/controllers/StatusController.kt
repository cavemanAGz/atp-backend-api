package com.caveman.atpbackendapi.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/status")
class StatusController() {

    @GetMapping("/health")
    fun health(): String {
        return "I AM ALIIIIIVVVVVVEEEEEE!"
    }

}
