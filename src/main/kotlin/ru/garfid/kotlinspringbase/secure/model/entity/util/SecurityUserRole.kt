package ru.garfid.kotlinspringbase.secure.model.entity.util

import ru.garfid.kotlinspringbase.core.model.entity.util.BaseEntity
import ru.garfid.kotlinspringbase.secure.model.entity.Role
import ru.garfid.kotlinspringbase.secure.model.entity.SecurityUser
import javax.persistence.*

@Entity
@Table(name = "security_auth_role")
class SecurityUserRole(
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "auth_id", nullable = false)
        val auth: SecurityUser = SecurityUser(),
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id", nullable = false)
        val role: Role = Role()
): BaseEntity()