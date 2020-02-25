package ru.garfid.artcenter.secure.logic.component.chain.provider

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import ru.garfid.artcenter.secure.logic.service.JwtService
import ru.garfid.artcenter.secure.logic.service.SignInUtilService
import ru.garfid.artcenter.secure.model.container.BasicAuthToken
import ru.garfid.artcenter.secure.model.container.JwToken

@Component
class LoginJwtProvider(
        val signInUtilService: SignInUtilService
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication? {
        val basicAuthToken = authentication as BasicAuthToken

        if (!signInUtilService.isValidUser(username = basicAuthToken.credentials, password = basicAuthToken.principal)) {
            throw UsernameNotFoundException("Wrong Username or Password")
        }

        println("Welcome, ${basicAuthToken.credentials}!")
        return JwToken(JwtService.makeTokenString(basicAuthToken.credentials))
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return (authentication == BasicAuthToken::class.java)
    }
}