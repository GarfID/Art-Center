package ru.garfid.artcenter.secure.model.container

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.GrantedAuthority
import ru.garfid.artcenter.secure.logic.service.JwtService

class JwToken constructor(
        @JsonIgnore
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

    @JsonIgnore
    override fun getName(): String {
        return credentials
    }

    @JsonIgnore
    override fun getCredentials(): String {
        val token = this.token
        return if(token != null) {
            JwtService.getSubject(token)
        } else {
            ""
        }

    }

    @JsonIgnore
    override fun getPrincipal(): String? {
        return token
    }

    @JsonIgnore
    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        return super.getAuthorities()
    }

    fun refresh() {
        val token = this.token
        requireNotNull(token)
        this.token = JwtService.refresh(token)
    }
}