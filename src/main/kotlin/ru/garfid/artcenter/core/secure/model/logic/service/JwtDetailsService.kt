package ru.garfid.artcenter.core.secure.model.logic.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.garfid.artcenter.core.model.repo.SystemUserRepo
import ru.garfid.artcenter.room.model.repo.RoomRepo
import ru.garfid.artcenter.core.secure.model.data.container.BaseUserDetails

@Service
class JwtDetailsService(
        val securityUserRepo: SystemUserRepo,
        val roomRepo: RoomRepo
): UserDetailsService{
    override fun loadUserByUsername(username: String): UserDetails {
        val securityUser = securityUserRepo.findByUsername(username) ?: throw UsernameNotFoundException("UD: No such user")

        return BaseUserDetails(securityUser, roomRepo)
    }
}