package com.caveman.atpbackendapi.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig() : WebSecurityConfigurerAdapter() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

//    @Bean
//    fun bCryptPasswordEncoder() = Argon2PasswordEncoder()
//    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity?): Unit {
        http!!
            .authorizeRequests { authorize ->
                authorize.antMatchers(
//                    "/",
                    "/swagger-resources/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**"
                ).permitAll()
            }
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic()
    }

    override fun userDetailsService(): UserDetailsService {
        val admin: UserDetails = User.withDefaultPasswordEncoder()
            .username("spring")
            .password("guru")
            .roles("ADMIN")
            .build()

        val user : UserDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(admin, user)

    }
}
