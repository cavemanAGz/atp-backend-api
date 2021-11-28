package com.caveman.atpbackendapi.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "app.security.jwt")
data class SecurityEnvironmentConfig(
    val key: String,
    val tokenTTL: Long,
    val rememberMeTTL: Long,
    val authorizationHeader: String,
    val tokenPrefix: String
)

@ConstructorBinding
@ConfigurationProperties(value = "app.security.argon2")
data class Argon2SecurityEnvironmentConfig(
    val saltLength: Int,
    val hashLength: Int,
    val parallelism: Int,
    val memory: Int,
    val iterations: Int
)
