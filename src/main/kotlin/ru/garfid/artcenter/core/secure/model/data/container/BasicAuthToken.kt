package ru.garfid.artcenter.core.secure.model.data.container

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class BasicAuthToken(
        private val authData: AuthContainer,
        tokenAuthorities: Collection<GrantedAuthority>? = null
) : AbstractAuthenticationToken(tokenAuthorities) {

    override fun getCredentials(): String {
        return authData.username
    }

    override fun getPrincipal(): String {
        return authData.password
    }
}
