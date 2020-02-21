package ru.garfid.kotlinspringbase.secure.model.container

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.web.servlet.function.ServerRequest
import ru.garfid.kotlinspringbase.secure.logic.JwtService
import javax.servlet.http.HttpServletRequest

class JwToken constructor(
        private var request: HttpServletRequest?,
        tokenAuthorities: Collection<GrantedAuthority>? = null
) : AbstractAuthenticationToken(tokenAuthorities) {
    private var usernameParameter = "username"
    private var passwordParameter = "password"

    private var tokenString: String? = null

    init {
        request?.cookies?.filter { it.name == "jwt" }?.map { tokenString = it.value }
        super.setAuthenticated(tokenAuthorities != null)
    }

    override fun getCredentials(): Any? {
        return obtainUsername()
    }

    override fun getPrincipal(): Any? {
        return obtainPassword()
    }

    fun getToken(): String? {
        return tokenString
    }

    fun setToken(newToken: String) {
        tokenString = newToken
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(isAuthenticated: Boolean) {
        require(!isAuthenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
        request = null
    }

    fun obtainPassword(): String {
        return request?.getParameter(passwordParameter) ?: ""
    }

    fun obtainUsername(): String {
        return request?.getParameter(usernameParameter) ?: ""
    }
}