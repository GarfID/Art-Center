package ru.garfid.kotlinspringbase.secure.logic

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.Assert
import ru.garfid.kotlinspringbase.secure.model.container.JwToken
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthFilter : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/login", "POST")) {
    private var usernameParameter = "username"
    private var passwordParameter = "password"
    private var postOnly = true

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse?): Authentication? {
        if (postOnly && request.method != "POST") {
            throw AuthenticationServiceException(
                    "Authentication method not supported: " + request.method)
        }

        val authRequest = JwToken(
                obtainUsername(request).trim { it <= ' ' }, obtainPassword(request))
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }

    fun obtainPassword(request: HttpServletRequest): String {
        return request.getParameter(passwordParameter)
    }

    fun obtainUsername(request: HttpServletRequest): String {
        return request.getParameter(usernameParameter)
    }

    fun setDetails(request: HttpServletRequest?,
                   authRequest: JwToken) {
        authRequest.details = authenticationDetailsSource.buildDetails(request)
    }

    fun setUsernameParameter(usernameParameter: String) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null")
        this.usernameParameter = usernameParameter
    }

    fun setPasswordParameter(passwordParameter: String) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null")
        this.passwordParameter = passwordParameter
    }

    fun setPostOnly(postOnly: Boolean) {
        this.postOnly = postOnly
    }

    fun getUsernameParameter(): String {
        return usernameParameter
    }

    fun getPasswordParameter(): String {
        return passwordParameter
    }
}