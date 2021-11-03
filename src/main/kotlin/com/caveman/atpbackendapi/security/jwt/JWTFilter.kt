package com.caveman.atpbackendapi.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/**
*  This is based on the JWTFilter from JHipster starter project. See:
*/
class JWTFilter(
    private val tokenProvider: TokenProvider
) : GenericFilterBean() {

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val TOKEN_HEADER = "Bearer "
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filter: FilterChain) {
        val httpReq = request as HttpServletRequest
        val jwt: String? = resolveToken(httpReq)

        if (!jwt.isNullOrBlank() && this.tokenProvider.validateToken(jwt)) {
            val auth = this.tokenProvider.getAuth(jwt)
            SecurityContextHolder.getContext().authentication = auth
        }

        filter.doFilter(request, response)
    }

    private fun resolveToken(req: HttpServletRequest): String? {

        val token = req.getHeader(AUTHORIZATION_HEADER)

        if (!token.isNullOrBlank() && token.startsWith(TOKEN_HEADER)) {
            return token.substring(7)
        }

        return null
    }

}
