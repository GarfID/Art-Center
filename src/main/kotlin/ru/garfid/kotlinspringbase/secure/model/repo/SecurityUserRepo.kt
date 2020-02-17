package ru.garfid.kotlinspringbase.secure.model.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.garfid.kotlinspringbase.secure.model.entity.SecurityUser

@Repository
interface SecurityUserRepo: JpaRepository<SecurityUser, Long> {
    fun findByUsername(login: String): SecurityUser?
}