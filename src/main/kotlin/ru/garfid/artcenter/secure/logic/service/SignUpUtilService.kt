package ru.garfid.artcenter.secure.logic.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.garfid.artcenter.core.model.entity.SystemUser
import ru.garfid.artcenter.core.model.repo.SystemUserRepo
import ru.garfid.artcenter.secure.model.entity.SecurityUser
import ru.garfid.artcenter.secure.model.repo.SecurityUserRepo

@Service
class SignUpUtilService(
        val systemUserRepo: SystemUserRepo,
        val securityUserRepo: SecurityUserRepo,
        val passwordEncoder: PasswordEncoder
) {

    fun isNewUsernameValid(username: String): Boolean {
        return !(username.length < 4 || securityUserRepo.existsByUsername(username))
    }

    fun isNewPasswordValid(password: String): Boolean {
        return password.length > 8
    }

    fun signUp(username: String, password: String): Boolean {
        var systemUser = SystemUser(displayName = username)
        val securityUser = SecurityUser(user = systemUser, password = passwordEncoder.encode(password))

        return try {
            systemUser.auth = securityUser
            systemUser = systemUserRepo.save(systemUser)
            systemUser.id != null
        } catch (e: Exception) {
            e.printStackTrace()

            false
        }
    }
}