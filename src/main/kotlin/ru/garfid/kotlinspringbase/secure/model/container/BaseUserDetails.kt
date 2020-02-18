package ru.garfid.kotlinspringbase.secure.model.container

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.garfid.kotlinspringbase.secure.model.entity.Capability
import ru.garfid.kotlinspringbase.secure.model.entity.SecurityUser

class BaseUserDetails(
        private val securityUser: SecurityUser
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val result: MutableList<Capability> = mutableListOf()
        securityUser.roles.forEach { userRole -> userRole.role.capabilities.forEach { result.add(it.capability) } }
        return result
    }

    override fun isEnabled() = securityUser.user?.deletedBy != null ?: false

    override fun getUsername() = securityUser.username

    override fun isCredentialsNonExpired() = securityUser.passwordExpired

    override fun getPassword() = securityUser.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = securityUser.locked
}