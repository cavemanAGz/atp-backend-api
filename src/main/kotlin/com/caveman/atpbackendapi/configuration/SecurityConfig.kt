package com.caveman.atpbackendapi.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig() : WebSecurityConfigurerAdapter() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun argon2PasswordEncoder() = Argon2PasswordEncoder()

    override fun configure(http: HttpSecurity?): Unit {
        http!!
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
