package com.caveman.atpbackendapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan(
	basePackages = [
		"com.caveman.atpbackendapi.security"
	]
)
class AtpBackendApiApplication

fun main(args: Array<String>) {
	runApplication<AtpBackendApiApplication>(*args)
}
