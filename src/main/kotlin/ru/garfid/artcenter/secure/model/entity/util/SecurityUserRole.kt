package ru.garfid.artcenter.secure.model.entity.util

import lombok.ToString
import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import ru.garfid.artcenter.secure.model.entity.Role
import ru.garfid.artcenter.secure.model.entity.SecurityUser
import javax.persistence.*

@Entity
@Table(name = "security_auth_role")
@ToString
class SecurityUserRole(
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "auth_id", nullable = false)
        val auth: SecurityUser = SecurityUser(),
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id", nullable = false)
        val role: Role = Role()
): BaseEntity()