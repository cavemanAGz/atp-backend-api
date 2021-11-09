package com.caveman.atpbackendapi.security

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.test.util.AssertionErrors.assertTrue

@ConfigurationPropertiesScan(basePackages = [
    "com.caveman.atpbackendapi.configuration"
])
@SpringBootTest
class SecurityTests(
    @Value("\${app.security.argon2.saltLength}")
    private val saltLength: Int,
    @Value("\${app.security.argon2.hashLength}")
    private val hashLength: Int,
    @Value("\${app.security.argon2.parallelism}")
    private val parallelism: Int,
    @Value("\${app.security.argon2.memory}")
    private val memory: Int,
    @Value("\${app.security.argon2.iterations}")
    private val iterations: Int
) {

    lateinit var argon2PasswordEncoder: Argon2PasswordEncoder

    @BeforeEach
    fun setUp() {
        argon2PasswordEncoder = Argon2PasswordEncoder(
            saltLength, hashLength, parallelism, memory, iterations
        )
    }

    @Test
    fun `Able to encode a password with Argon 2`() {
        val result = argon2PasswordEncoder.encode("password")
        println("Hashed Value: $result")
        val valid = argon2PasswordEncoder.matches("password", result)
        assertTrue("Valid password hashed correctly", valid)
    }

    @Test
    fun `Able to match a password with an Argon 2 hash`() {
        val valid = argon2PasswordEncoder.matches("password", "\$argon2id\$v=19\$m=65536,t=10,p=4\$uSO67yT8r8jJpsv5Avvg4Q\$waVRqx76y85dnbiMi2YrtAn7AXoMABmCDQEbkzGsk38")
        assertTrue("Hashed Password matches", valid)
    }


}
