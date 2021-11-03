package com.caveman.atpbackendapi.configuration

import com.caveman.atpbackendapi.security.jwt.JWTConfigurationManager
import com.caveman.atpbackendapi.security.jwt.TokenProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val corsFilter: CorsFilter) : WebSecurityConfigurerAdapter() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
//    fun bCryptPasswordEncoder() = Argon2PasswordEncoder()
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity?) {
        // TODO: What does this do?
        web!!.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
    }

    override fun configure(http: HttpSecurity?) {
        http!!
            .authorizeRequests { authorize ->
                authorize.antMatchers(
                    "/",
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

//    override fun configure(http: HttpSecurity?) {
//        http!!
//            .csrf()
//            .disable()
//            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .exceptionHandling()
//            .and()
//            .headers()
//            .frameOptions()
//            .deny()
//            .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests { authorize ->
//                authorize.antMatchers(
////                    "/",
//                    "/swagger-resources/**",
//                    "/v3/api-docs/**",
//                    "/swagger-ui/**"
//                ).permitAll()
//            }.authorizeRequests()
//            .anyRequest().authenticated()
//            .and()
//            .httpBasic()
//            .and()
//            .formLogin()
//            .and()
//            .apply(securityConfigAdapter())
//    }


//    private fun securityConfigAdapter() = JWTConfigurationManager(tokenProvider = tokenProvider)
}
