package ru.garfid.kotlinspringbase.secure.model.entity.util

import ru.garfid.kotlinspringbase.core.model.entity.util.BaseEntity
import ru.garfid.kotlinspringbase.secure.model.entity.Capability
import ru.garfid.kotlinspringbase.secure.model.entity.Role
import javax.persistence.*

@Entity
@Table(name = "security_role_capability")
class RoleCapability(
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id", nullable = false)
        val role: Role = Role(),
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "cap_id", nullable = false)
        val capability: Capability = Capability(AuthorityAccessLevelEnum.VIEW)
): BaseEntity()