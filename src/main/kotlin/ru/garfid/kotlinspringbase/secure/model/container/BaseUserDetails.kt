package ru.garfid.kotlinspringbase.secure.model.container

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.garfid.kotlinspringbase.secure.model.entity.SecurityUser

class BaseUserDetails(
        val securityUser: SecurityUser
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEnabled() = securityUser.user?.deletedBy != null ?: false

    override fun getUsername() = securityUser.username

    override fun isCredentialsNonExpired() = securityUser.passwordExpired

    override fun getPassword() = securityUser.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = securityUser.locked
}