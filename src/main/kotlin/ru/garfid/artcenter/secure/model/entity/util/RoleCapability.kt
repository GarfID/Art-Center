package ru.garfid.artcenter.secure.model.entity.util

import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import ru.garfid.artcenter.secure.model.entity.AuthorityAccessLevelEnum
import ru.garfid.artcenter.secure.model.entity.AuthorityBlockEnum
import ru.garfid.artcenter.secure.model.entity.Capability
import ru.garfid.artcenter.secure.model.entity.Role
import javax.persistence.*

@Entity
@Table(name = "security_role_capability")
class RoleCapability(
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id", nullable = false)
        val role: Role = Role(),
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "cap_id", nullable = false)
        val capability: Capability = Capability(AuthorityBlockEnum.NONE, AuthorityAccessLevelEnum.NONE)
): BaseEntity()