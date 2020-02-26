package ru.garfid.artcenter.secure.model.container

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.garfid.artcenter.core.model.entity.SystemUser
import ru.garfid.artcenter.core.model.entity.linkers.RoomUsersCapability

class BaseUserDetails(
        val systemUser: SystemUser
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf<RoomUsersCapability>()
    }

    override fun isEnabled() = systemUser.deletedBy != null ?: false

    @JsonIgnore
    override fun getUsername() = systemUser.displayName

    override fun isCredentialsNonExpired() = systemUser.passwordExpired

    @JsonIgnore
    override fun getPassword() = systemUser.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = systemUser.locked

    override fun toString(): String {
        return systemUser.toString()
    }
}