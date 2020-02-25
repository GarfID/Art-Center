package ru.garfid.artcenter.secure.logic.component.chain.filter

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import ru.garfid.artcenter.secure.logic.component.chain.manager.JwtAuthManager
import ru.garfid.artcenter.secure.model.container.BaseUserDetails
import ru.garfid.artcenter.secure.model.container.JwToken
import ru.garfid.artcenter.secure.model.repo.SecurityUserRepo
import java.net.URL
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthJwtFilter(
        authManager: JwtAuthManager,
        val securityUserRepo: SecurityUserRepo
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/**", "POST")) {

    init {
        super.setAuthenticationManager(authManager)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse): Authentication {
        if (request.method != "POST") {
            throw AuthenticationServiceException(
                    "Authentication method not supported: " + request.method)
        }

        val jwtToken = JwToken(request.cookies?.firstOrNull { it.name == "jwt" }?.value)

        return if (SecurityContextHolder.getContext().authentication != null) {
            SecurityContextHolder.getContext().authentication
        } else {
            jwtToken.refresh()
            authenticationManager.authenticate(jwtToken)
        }
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

        if(authResult is JwToken && authResult.isValid()) {
            response.addCookie(Cookie("jwt", authResult.token))
        }

        successfulAuthentication(request, response, chain, authResult)
    }

    override fun requiresAuthentication(request: HttpServletRequest, response: HttpServletResponse): Boolean {
        val ignoredPaths: Array<String> = arrayOf("/security/signup", "/error")

        val tail = URL(request.requestURL.toString()).file

        if (tail in ignoredPaths) {
            if (logger.isDebugEnabled) {
                logger.debug("$tail is in a skip list. This is none of my business")
            }

            return false
        }

        if (logger.isDebugEnabled) {
            logger.debug("$tail is in a skip list. This is none of my business")
        }

        return true
    }
}