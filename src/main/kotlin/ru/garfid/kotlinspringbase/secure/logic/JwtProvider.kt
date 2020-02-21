package ru.garfid.kotlinspringbase.secure.logic

import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import ru.garfid.kotlinspringbase.secure.model.container.JwToken

@Component
class JwtProvider: AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        if(authentication::class == JwToken::class) {
            val jwToken: JwToken = authentication as JwToken

            if (jwToken.getToken() == null) {
                jwToken.setToken(JwtService.makeTokenString(jwToken.credentials as String))
            } else {
                println("IT WORKED! I KNOW THIS TOKEN! You're " + jwToken.credentials)
            }

            return jwToken
        } else {
            throw InternalAuthenticationServiceException("unsupported auth class")
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return (authentication == JwToken::class.java || authentication == UsernamePasswordAuthenticationToken::class.java)
    }
}