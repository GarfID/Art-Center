package ru.garfid.artcenter.secure.model.container

import lombok.ToString
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.garfid.artcenter.secure.model.entity.Capability
import ru.garfid.artcenter.secure.model.entity.SecurityUser

class BaseUserDetails(
        val securityUser: SecurityUser
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val result: MutableList<Capability> = mutableListOf()
        securityUser.roles.forEach { userRole -> userRole.role.capabilities.forEach { result.add(it.capability) } }
        return result
    }

    override fun isEnabled() = securityUser.user.deletedBy != null ?: false

    override fun getUsername() = securityUser.user.displayName

    override fun isCredentialsNonExpired() = securityUser.passwordExpired

    override fun getPassword() = securityUser.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = securityUser.locked

    override fun toString(): String {
        return securityUser.toString()
    }
}