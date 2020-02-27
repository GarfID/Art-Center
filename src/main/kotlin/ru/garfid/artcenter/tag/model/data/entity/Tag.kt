package ru.garfid.artcenter.tag.model.data.entity

import ru.garfid.artcenter.core.model.entity.util.SimpleEntity
import java.awt.Label
import javax.persistence.*

@Entity
@Table(name = "image_file")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class Tag(
        @Column(nullable = false, unique = true)
        val e621_label: String = ""
): SimpleEntity()