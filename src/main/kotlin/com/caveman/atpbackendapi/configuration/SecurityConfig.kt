package com.caveman.atpbackendapi.configuration

import RestHeaderAuthFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
    @Value("\${app.security.argon2.saltLength}")
    private val saltLength: Int,
    @Value("\${app.security.argon2.hashLength}")
    private val hashLength: Int,
    @Value("\${app.security.argon2.parallelism}")
    private val parallelism: Int,
    @Value("\${app.security.argon2.memory}")
    private val memory: Int,
    @Value("\${app.security.argon2.iterations}")
    private val iterations: Int,
) : WebSecurityConfigurerAdapter() {

    companion object {
        val localLogger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun argon2PasswordEncoder() = Argon2PasswordEncoder(saltLength,hashLength,parallelism,memory,iterations)

    fun restHeaderAuthFilter(authManager: AuthenticationManager): RestHeaderAuthFilter {
        val filter = RestHeaderAuthFilter("/**")
        filter.setAuthenticationManager(authManager)
        return filter
    }

    override fun configure(http: HttpSecurity?) {
        http!!.addFilterBefore(
            restHeaderAuthFilter(authenticationManager()),
            UsernamePasswordAuthenticationFilter::class.java
        )
//            .csrf()
//            .disable()
            .authorizeRequests { authorize ->
                authorize.antMatchers(
//                    "/",
                    "/status/health/**",
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

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("\$argon2id\$v=19\$m=65536,t=10,p=4\$n2p5BpLoTJSspbsH0J+NRg\$FwEvuKvYHgiE6BVj7pphe8YesDpaFZMHrSlsK7UyFCE")
            .roles("ADMIN")
            .and()
            .withUser("user")
            .password("\$argon2id\$v=19\$m=65536,t=10,p=4\$n2p5BpLoTJSspbsH0J+NRg\$FwEvuKvYHgiE6BVj7pphe8YesDpaFZMHrSlsK7UyFCE")
            .roles("USER")
            .and()
            .withUser("customer")
            .password("\$argon2id\$v=19\$m=65536,t=10,p=4\$n2p5BpLoTJSspbsH0J+NRg\$FwEvuKvYHgiE6BVj7pphe8YesDpaFZMHrSlsK7UyFCE")
            .roles("CUSTOMER")
    }

//    override fun userDetailsService(): UserDetailsService {
//        val admin: UserDetails = User.withDefaultPasswordEncoder()
//            .username("spring")
////            .password("guru")
////            .password("password")
//            .password("\$argon2id\$v=19\$m=65536,t=10,p=4\$n2p5BpLoTJSspbsH0J+NRg\$FwEvuKvYHgiE6BVj7pphe8YesDpaFZMHrSlsK7UyFCE")
//            .roles("ADMIN")
//            .build()
//
//        val user : UserDetails = User.withDefaultPasswordEncoder()
//            .username("user")
////            .password("password")
//            .password("\$argon2id\$v=19\$m=65536,t=10,p=4\$n2p5BpLoTJSspbsH0J+NRg\$FwEvuKvYHgiE6BVj7pphe8YesDpaFZMHrSlsK7UyFCE")
//            .roles("USER")
//            .build()
//
//        return InMemoryUserDetailsManager(admin, user)
//
//    }
}
