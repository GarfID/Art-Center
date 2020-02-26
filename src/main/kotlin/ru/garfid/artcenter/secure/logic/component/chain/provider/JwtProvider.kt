package ru.garfid.artcenter.secure.logic.component.chain.provider

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.garfid.artcenter.secure.logic.service.JwtDetailsService
import ru.garfid.artcenter.secure.logic.service.SignInUtilService
import ru.garfid.artcenter.secure.model.container.JwToken

@Service
class JwtProvider(
        val signInUtilService: SignInUtilService,
        val jwtDetailsService: JwtDetailsService
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val jwToken: JwToken = authentication as JwToken

        if(jwToken.principal == null) {
            throw BadCredentialsException("No token found")
        }

        if (!jwToken.isValid()) {
            throw BadCredentialsException("Invalid JWT")
        }

        if(!signInUtilService.isValidUser(jwToken.credentials)) {
            throw UsernameNotFoundException("No such user found " + jwToken.credentials)
        }

        println("IT WORKED! I KNOW THIS TOKEN! You're ${jwToken.credentials}")

        jwToken.refresh()
        jwToken.isAuthenticated = true
        jwToken.details = jwtDetailsService.loadUserByUsername(jwToken.credentials)

        return jwToken
    }

    override fun supports(authentication: Class<*>): Boolean {
        return (authentication == JwToken::class.java)
    }
}