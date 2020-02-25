package ru.garfid.artcenter.secure.model.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.garfid.artcenter.secure.model.entity.Role

@Repository
interface RoleRepo: JpaRepository<Role, Long> {
}