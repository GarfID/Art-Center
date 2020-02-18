package ru.garfid.kotlinspringbase.core.model.entity

import ru.garfid.kotlinspringbase.core.model.entity.util.OwnableBaseEntity
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Entity
@Table(name = "core_room")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class Room(
        val name: String = ""
): OwnableBaseEntity()