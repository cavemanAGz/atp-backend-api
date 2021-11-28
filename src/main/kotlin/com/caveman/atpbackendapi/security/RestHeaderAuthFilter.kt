import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RestHeaderAuthFilter(url: String): AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    companion object {
        val localLogger: Logger = LoggerFactory.getLogger(RestHeaderAuthFilter::class.java)
    }

    // This is what spring security will use once the filter is in place to auth a user in the way I want it to happen
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {
        val userName = getUserName(request) ?: ""
        val password = getPassword(request) ?: ""

        val token = UsernamePasswordAuthenticationToken(
            userName, password
        )

        localLogger.debug("Attempting to login user: $userName")

        return if (userName.isNotEmpty()) {
            authenticationManager.authenticate(token)
        } else {
            null
        }

    }

    private fun getPassword(request: HttpServletRequest?): String? {
        return request?.getHeader("password")
    }

    private fun getUserName(request: HttpServletRequest?): String? {
        return request?.getHeader("username")
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val req = request as HttpServletRequest
        val resp = response as HttpServletResponse

        // attempt authentication method is a default spring sec method that will be overridden
        try {
            val authResult = attemptAuthentication(request, response)

            if (authResult != null) {
                successfulAuthentication(req, resp, chain, authResult)
            } else {
                chain?.doFilter(req, resp)
            }
        } catch (authenticationException: AuthenticationException) {
            unsuccessfulAuthentication(req, resp, authenticationException)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {

        // What happens when authentication is successful.....
        localLogger.debug("Authentication success. Updating security context")

        SecurityContextHolder.getContext().authentication = authResult

    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        failed: AuthenticationException?
    ) {
        // Clear the any valid security context, e they are not authenticated
        SecurityContextHolder.clearContext()

        localLogger.debug("Authentication Request FAILED!!!")

        response?.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)

    }


}