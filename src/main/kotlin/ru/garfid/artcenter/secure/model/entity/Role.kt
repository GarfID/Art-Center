package ru.garfid.artcenter.secure.model.entity

import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import ru.garfid.artcenter.secure.model.entity.util.RoleCapability
import javax.persistence.*

@Entity(name = "Role")
@Table(name = "security_role")
class Role(
        val label: String = "NONE",
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "capability")
        val capabilities: MutableList<RoleCapability> = mutableListOf()
): BaseEntity()