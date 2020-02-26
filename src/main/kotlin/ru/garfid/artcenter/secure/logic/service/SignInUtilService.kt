package ru.garfid.artcenter.secure.logic.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.garfid.artcenter.core.model.repo.SystemUserRepo

@Service
class SignInUtilService(
        val systemUserRepo: SystemUserRepo,
        val passwordEncoder: PasswordEncoder
) {
    fun isValidUser(username: String): Boolean {
        return systemUserRepo.existsByUsername(username)
    }

    fun isValidUser(username: String, password: String): Boolean {
        val user = systemUserRepo.findByUsername(username) ?: return false
        return passwordEncoder.matches(password, user.password)
    }
}