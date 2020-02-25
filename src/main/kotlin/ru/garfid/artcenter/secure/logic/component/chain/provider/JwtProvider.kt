package ru.garfid.artcenter.secure.logic.component.chain.provider

import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import ru.garfid.artcenter.secure.logic.service.JwtService
import ru.garfid.artcenter.secure.logic.service.SignInUtilService
import ru.garfid.artcenter.secure.model.container.JwToken

@Component
class JwtProvider(
        val signInUtilService: SignInUtilService
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

        if(signInUtilService.isValidUser(jwToken.name)) {
            throw UsernameNotFoundException("No such user found")
        }

        println("IT WORKED! I KNOW THIS TOKEN! You're ${jwToken.name}")
        jwToken.refresh()

        return jwToken
    }

    override fun supports(authentication: Class<*>): Boolean {
        return (authentication == JwToken::class.java)
    }
}