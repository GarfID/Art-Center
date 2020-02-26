package ru.garfid.artcenter.secure.logic.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.garfid.artcenter.core.model.repo.SystemUserRepo
import ru.garfid.artcenter.secure.model.container.BaseUserDetails

@Service
class JwtDetailsService(
        val securityUserRepo: SystemUserRepo
): UserDetailsService{
    override fun loadUserByUsername(username: String): UserDetails {
        val securityUser = securityUserRepo.findByUsername(username) ?: throw UsernameNotFoundException("UD: No such user")

        return BaseUserDetails(securityUser)
    }
}