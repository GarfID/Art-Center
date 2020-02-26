package ru.garfid.artcenter.core.model.entity.linkers

import ru.garfid.artcenter.core.model.entity.Room
import ru.garfid.artcenter.core.model.entity.SystemUser
import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "core_room_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class RoomUser(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_id", nullable = false)
        val room: Room = Room(),
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: SystemUser = SystemUser(),
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "roomUser")
        val capabilities: MutableList<RoomUsersCapability> = mutableListOf()
) : BaseEntity()