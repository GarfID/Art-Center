package ru.garfid.artcenter.secure.model.container

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.GrantedAuthority
import ru.garfid.artcenter.secure.logic.service.JwtService

class JwToken constructor(
        var token: String? = null,
        tokenAuthorities: Collection<GrantedAuthority>? = null
) : AbstractAuthenticationToken(tokenAuthorities) {

    init {
        super.setAuthenticated(tokenAuthorities != null)
    }

    fun isValid(): Boolean {
        return try {
            JwtService.checkToken(token ?: "")
            true
        } catch (e: BadCredentialsException) {
            false
        }
    }

    override fun getCredentials(): Any? {
        return ""
    }

    override fun getPrincipal(): Any? {
        return token
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(isAuthenticated: Boolean) {
        require(!isAuthenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
    }

    fun refresh() {
        val token = this.token
        requireNotNull(token)
        this.token = JwtService.refresh(token)
    }
}