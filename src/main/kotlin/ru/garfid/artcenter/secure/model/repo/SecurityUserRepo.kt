package ru.garfid.artcenter.secure.model.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.garfid.artcenter.secure.model.entity.SecurityUser

@Repository
interface SecurityUserRepo: JpaRepository<SecurityUser, Long> {
    @Query("SELECT count(su)>0 FROM SecurityUser su WHERE su.user.displayName = :username")
    fun existsByUsername(username: String): Boolean

    @Query("SELECT case when count(su)> 0 then true else false end FROM SecurityUser su WHERE su.user.displayName = :username AND su.password = :password")
    fun existsByUsernameAndPassword(username: String, password: String): Boolean

    @Query("SELECT su FROM SecurityUser su WHERE su.user.displayName = :username")
    fun findByUsername(username: String): SecurityUser?
}