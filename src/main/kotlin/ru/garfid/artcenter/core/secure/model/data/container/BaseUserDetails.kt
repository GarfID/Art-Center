package ru.garfid.artcenter.core.secure.model.data.container

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.UserDetails
import ru.garfid.artcenter.core.model.entity.SystemUser
import ru.garfid.artcenter.room.model.entity.RoomUsersCapability
import ru.garfid.artcenter.room.model.repo.RoomRepo

class BaseUserDetails(
        val systemUser: SystemUser,
        @JsonIgnore
        val roomRepo: RoomRepo
) : UserDetails {
    override fun getAuthorities(): MutableCollection<RoomUsersCapability> {
        if(systemUser.id != null) {
            val systemUserRoomUsers = roomRepo.findRoomContainingUser(systemUser.id)
            val result: MutableList<RoomUsersCapability> = mutableListOf()
            systemUserRoomUsers.forEach{ it.capabilities.forEach { capability -> result += capability }}
            return result
        }
        return mutableListOf()
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