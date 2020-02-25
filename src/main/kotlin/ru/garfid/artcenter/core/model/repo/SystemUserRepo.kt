package ru.garfid.artcenter.core.model.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.garfid.artcenter.core.model.entity.SystemUser

@Repository
interface SystemUserRepo: JpaRepository<SystemUser, Long> {
}