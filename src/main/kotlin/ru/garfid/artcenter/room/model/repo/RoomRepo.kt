package ru.garfid.artcenter.room.model.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.garfid.artcenter.room.model.entity.Room
import ru.garfid.artcenter.room.model.entity.RoomUser

@Repository
interface RoomRepo: JpaRepository<Room, Long> {
    @Query("SELECT ru FROM Room r INNER JOIN r.roomUsers ru WHERE ru.user.id = :userId")
    fun findRoomContainingUser(userId: Long): MutableCollection<RoomUser>
}