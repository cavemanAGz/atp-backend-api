package com.caveman.atpbackendapi.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class Root() {
    @GetMapping
    fun root(): String {
        return "root"
    }

}
