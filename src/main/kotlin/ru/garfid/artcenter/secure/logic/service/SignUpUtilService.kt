package ru.garfid.artcenter.secure.logic.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.garfid.artcenter.core.model.entity.SystemUser
import ru.garfid.artcenter.core.model.repo.SystemUserRepo

@Service
class SignUpUtilService(
        val systemUserRepo: SystemUserRepo,
        val passwordEncoder: PasswordEncoder
) {

    fun isUsernameTaken(username: String): Boolean {
        return systemUserRepo.existsByUsername(username)
    }

    fun isNewPasswordValid(password: String): Boolean {
        return password.length > 8
    }

    fun signUp(username: String, password: String): Boolean {
        var systemUser = SystemUser(username = username, displayName = username, password = passwordEncoder.encode(password))

        return try {
            systemUser = systemUserRepo.save(systemUser)
            systemUser.id != null
        } catch (e: Exception) {
            e.printStackTrace()

            false
        }
    }
}