package ru.garfid.kotlinspringbase.secure.model.container

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.SpringSecurityCoreVersion

class JwToken constructor(
        private var principal: Any,
        private var credentials: Any,
        private val tokenAuthorities: Collection<GrantedAuthority>? = null
) : AbstractAuthenticationToken(tokenAuthorities) {
    private val serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID

    init {
        super.setAuthenticated(tokenAuthorities != null)
    }

    override fun getCredentials(): Any? {
        return credentials
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(isAuthenticated: Boolean) {
        require(!isAuthenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
        credentials = Unit
    }
}