package com.caveman.atpbackendapi.security.jwt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTConfigurationManager(private val tokenProvider: TokenProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(JWTConfigurationManager::class.java)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        val myFilter = JWTFilter(tokenProvider)
        logger.debug("Adding the JWT Filter Config. (Following a JHipster project for help!)")
        http!!.addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
