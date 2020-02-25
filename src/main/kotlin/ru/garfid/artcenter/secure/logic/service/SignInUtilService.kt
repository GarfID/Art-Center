package ru.garfid.artcenter.secure.logic.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.garfid.artcenter.secure.model.repo.SecurityUserRepo

@Service
class SignInUtilService(
        val securityUserRepo: SecurityUserRepo,
        val passwordEncoder: PasswordEncoder
) {
    fun isValidUser(username: String): Boolean {
        return securityUserRepo.existsByUsername(username)
    }

    fun isValidUser(username: String, password: String): Boolean {
        val securityUser = securityUserRepo.findByUsername(username) ?: return false
        return passwordEncoder.matches(password, securityUser.password)
    }
}