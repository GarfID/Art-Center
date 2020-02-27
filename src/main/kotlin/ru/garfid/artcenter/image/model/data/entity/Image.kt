package ru.garfid.artcenter.image.model.data.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import lombok.EqualsAndHashCode
import ru.garfid.artcenter.core.model.entity.util.OwnableBaseEntity
import ru.garfid.artcenter.room.model.entity.Room
import javax.persistence.*

@Entity
@Table(name = "image")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class Image (
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_id", nullable = false)
        val room: Room = Room(),
        @Column(nullable = false)
        val name: String = "",
        @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
        @JsonManagedReference
        val file: ImageFile = ImageFile()
): OwnableBaseEntity()