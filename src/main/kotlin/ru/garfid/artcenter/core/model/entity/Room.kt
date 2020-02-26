package ru.garfid.artcenter.core.model.entity

import ru.garfid.artcenter.core.model.entity.linkers.RoomUser
import ru.garfid.artcenter.core.model.entity.util.OwnableBaseEntity
import javax.persistence.*

@Entity
@Table(name = "core_room")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class Room(
        @Column(nullable = false)
        val name: String = "",
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "room")
        val roomUsers: MutableList<RoomUser> = mutableListOf()
): OwnableBaseEntity()