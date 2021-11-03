package com.caveman.atpbackendapi.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.core.env.Environment
import javax.servlet.ServletContext
import javax.servlet.ServletException

@Configuration
class WebConfig(
    private val env: Environment
) : ServletContextInitializer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Throws(ServletException::class)
    override fun onStartup(servletContext: ServletContext) {
        if(env.activeProfiles.isNotEmpty()) {
            logger.info("Using Profile: {}", *env.activeProfiles as Array<*>)
        }
    }


    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:3000")
        if (config.allowedOrigins != null && config.allowedOrigins!!.isNotEmpty()) {
            logger.debug("Registering CORS filter")
            logger.debug("CORS Allowed: ${config.allowedOrigins.toString()}")
            source.apply {
                registerCorsConfiguration("/api/**", config)
                registerCorsConfiguration("/management/**", config)
                registerCorsConfiguration("/v2/api-docs", config)
            }
        }
        return CorsFilter(source)
    }


}