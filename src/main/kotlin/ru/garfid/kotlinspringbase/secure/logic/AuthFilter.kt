package ru.garfid.kotlinspringbase.secure.logic

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import ru.garfid.kotlinspringbase.secure.model.container.JwToken
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthFilter(
        authManager: AuthManager
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/login", "POST")) {
    private var postOnly = false

    init {
        super.setAuthenticationManager(authManager)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse?): Authentication? {
        if (postOnly && request.method != "POST") {
            throw AuthenticationServiceException(
                    "Authentication method not supported: " + request.method)
        }

        val authRequest = JwToken(request)
        setDetails(request, authRequest)
        val result = authenticationManager.authenticate(authRequest)
        return result
    }

    fun setDetails(request: HttpServletRequest?,
                   authRequest: JwToken) {
        authRequest.details = authenticationDetailsSource.buildDetails(request)
    }

    fun setPostOnly(postOnly: Boolean) {
        this.postOnly = postOnly
    }

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response)
            return
        }

        if (logger.isDebugEnabled) {
            logger.debug("Request is to process authentication")
        }

        val authResult: Authentication?

        try {
            authResult = attemptAuthentication(request, response)
            if (authResult == null) {
                // return immediately as subclass has indicated that it hasn't completed
                // authentication
                return
            }
        } catch (failed: InternalAuthenticationServiceException) {
            logger.error(
                    "An internal error occurred while trying to authenticate the user.",
                    failed)
            unsuccessfulAuthentication(request, response, failed)
            return
        } catch (failed: AuthenticationException) { // Authentication failed
            unsuccessfulAuthentication(request, response, failed)
            return
        }

        val authToken = (authResult as JwToken).getToken()
        if(authToken != null) {
            response.addCookie(Cookie("jwt", authToken))
            successfulAuthentication(request, response, chain, authResult)
        } else {
            unsuccessfulAuthentication(request, response, BadCredentialsException("No token in auth result"))
        }
    }
}