package ru.garfid.kotlinspringbase.secure.model.entity

import ru.garfid.kotlinspringbase.core.model.entity.util.BaseEntity
import ru.garfid.kotlinspringbase.secure.model.entity.util.RoleCapability
import javax.persistence.*

@Entity
@Table(name = "security_role")
class Role(
        val label: String = "NONE",
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "capability")
        val capabilities: MutableList<RoleCapability> = mutableListOf()
): BaseEntity()