package com.caveman.atpbackendapi.security.jwt

import com.caveman.atpbackendapi.security.SecurityEnvironmentConfig
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TokenProvider(
    private val securityEnvironmentConfig: SecurityEnvironmentConfig
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        private val AUTHORITIES_KEY = "auth"
    }

    private var key: Key? = null
    private var tokenTTL: Long = 0
    private var rememberMeTokenTTL: Long = 0

    @PostConstruct
    fun init() {
        val keyBytes: ByteArray

        // This regex to check base64 encoding found at: https://newbedev.com/how-to-check-whether-a-string-is-base64-encoded-or-not
        val base64EncodedRegEx = Regex("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?\$")

        val secret = securityEnvironmentConfig.key

        keyBytes = if (secret.isNullOrBlank()) {
            logger.warn("Key is empty or null")
            throw RuntimeException("No JWT key present in configuration, you should fix that!")
        } else if (!base64EncodedRegEx.matches(secret)) {
            logger.warn("Key is not base 64 encoded, you should fix that!")
            secret.toByteArray(StandardCharsets.UTF_8)
        } else {
            logger.info("Using a base 64 encoded key... sweet!")
            Decoders.BASE64.decode(securityEnvironmentConfig.key)
        }

        this.key = Keys.hmacShaKeyFor(keyBytes)
        this.tokenTTL = securityEnvironmentConfig.tokenTTL
        this.rememberMeTokenTTL = securityEnvironmentConfig.rememberMeTTL
    }

    fun createToken(auth: Authentication, rememberMe: Boolean): String {
        val authorities: String = auth.authorities
            .asSequence()
            .map { curAuth -> curAuth.authority }
            .joinToString(separator = ",")

        val now: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val validity = if (rememberMe) {
            // These shenanigans to get a Date from what I am told is a more reliable library Locale Dat Time
            Date
                .from(LocalDateTime
                    .ofEpochSecond(now + this.rememberMeTokenTTL, 0, ZoneOffset.UTC)
                    .atZone(ZoneOffset.UTC)
                    .toInstant()
                )
        } else {
            Date.from(LocalDateTime
                .ofEpochSecond(now + this.tokenTTL, 0, ZoneOffset.UTC)
                .atZone(ZoneOffset.UTC)
                .toInstant()
            )
        }

        return Jwts.builder()
            .setSubject(auth.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getAuth(token: String): Authentication {

        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val authorities = claims[AUTHORITIES_KEY]
            .toString()
            .splitToSequence(",")
            .mapTo(mutableListOf()) { claim ->
                SimpleGrantedAuthority(claim)
            }

        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: JwtException) {
            logger.info("Invalid JWT")
        } catch (e: IllegalArgumentException) {
            logger.info("Invalid JWT")
        }

        return false

    }

}