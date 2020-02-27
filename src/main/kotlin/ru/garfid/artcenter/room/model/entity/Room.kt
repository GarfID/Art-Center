package ru.garfid.artcenter.room.model.entity

import ru.garfid.artcenter.room.model.entity.RoomUser
import ru.garfid.artcenter.core.model.entity.util.OwnableBaseEntity
import ru.garfid.artcenter.image.model.data.entity.Image
import javax.persistence.*

@Entity
@Table(name = "core_room")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class Room(
        @Column(nullable = false)
        val name: String = "",
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "room")
        val roomUsers: MutableList<RoomUser> = mutableListOf(),
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "room")
        val roomImages: MutableList<Image> = mutableListOf()
): OwnableBaseEntity()